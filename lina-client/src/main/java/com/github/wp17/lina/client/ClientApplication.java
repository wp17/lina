package com.github.wp17.lina.client;

public class ClientApplication {
    private static Client client;

    public static void main(String[] args) throws InterruptedException {
        client = new Client();
        client.startup();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> client.shutdown()));
    }
}
