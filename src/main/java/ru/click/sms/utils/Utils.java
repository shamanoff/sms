package ru.click.sms.utils;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Вспомогательный класс
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public abstract class Utils {

    public static void sleep(long delay) {
        sleep(SECONDS, delay);
    }

    public static void sleep(TimeUnit unit, long delay) {
        try {
            unit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
