package me.ankhell.anonymous.chat.bot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AnonymousChatBotCommandTest {

    @Test
    @Disabled
    public void testWithCommandLineOption() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.DEVELOPMENT, Environment.TEST)) {
            PicocliRunner.run(AnonymousChatBotCommand.class, ctx);

            // anonymous-chat-bot
            assertTrue(baos.toString().contains("Your bot is up and running"));
        }
    }
}
