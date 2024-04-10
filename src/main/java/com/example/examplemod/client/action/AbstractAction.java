package com.example.examplemod.client.action;

public abstract class AbstractAction {
    public AbstractAction() {

    }

    protected int cooldown;
    protected int inputTime;
    protected int stopTime;

    protected abstract void run();
}
