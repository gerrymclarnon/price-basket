package stepdefs;

import cucumber.api.java8.En;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.lang.Runtime.*;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StepDefinitions implements En {

    private String output = "";

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output += output;
    }

    public StepDefinitions() {
        Given("^I have built the PriceBasket application$", () -> {
            try {
                final StringBuilder dirListing = new StringBuilder();
                getRuntime().exec("cd ../..");

                Process process = getRuntime().exec("ls target");
                StreamGobbler streamGobbler =
                        new StreamGobbler(process.getInputStream(), output -> dirListing.append(output));
                int exitCode = process.waitFor();
                assert exitCode == 0;

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(streamGobbler);
                executor.shutdown();
                executor.awaitTermination(10, SECONDS);
                assert dirListing.toString().contains("price-basket-0.0.1-SNAPSHOT.jar");
            } catch (IOException|InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    getRuntime().exec("cd -");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        When("^I run PriceBasket([^\\\"]*)", (String products) -> {
            try {
                getRuntime().exec("cd ../..");

                Process process = getRuntime().exec("./PriceBasket  " + products);
                int exitCode = process.waitFor();
                assert exitCode == 0;

                StreamGobbler streamGobbler =
                        new StreamGobbler(process.getInputStream(), output -> setOutput(output));
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(streamGobbler);
                executor.shutdown();
                executor.awaitTermination(10, SECONDS);
            } catch (IOException|InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    getRuntime().exec("cd -");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Then("^the Priced Basket shows ([^\\\"]*)$", (String text) -> {
            assert this.getOutput().contains(text);
        });

        Then("^the output shows Unexpected item \"([^\"]*)\" found in basket!$", (String text) -> {
            assert this.getOutput().contains(String.format("Unexpected item \"%s\" found in basket!", text));
        });

        Then("^the output shows Usage: PriceBasket Apples Bread Soup Milk$", () -> {
            assert this.getOutput().contains("Usage: PriceBasket Apples Bread Soup Milk");
        });
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }
}