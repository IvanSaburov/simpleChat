package ru.simplechat.command;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandPoolTest {


    @Test
    public void testRunCommand() throws Exception {
        assertNotNull(CommandPool.runCommand("help", "tester"));
    }

    @Test
    public void testGetCommandMap() throws Exception {
        assertThat(CommandPool.getCommandMap(), IsMapContaining.hasKey("help"));
    }
}