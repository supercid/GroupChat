package com.company;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException,IOException{

        final Client client = new Client();
        client.setVisible(true);
    }
}
