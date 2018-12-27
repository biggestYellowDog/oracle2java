/*
package hello;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final BookRepository bookRepository;

    public AppRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Test
    public void run(String... args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
        String now = format.format(new Date());
        logger.info(now+":.... Fetching books");
        logger.info(now+":isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info(now+":isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info(now+":isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info(now+":isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info(now+":isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info(now+":isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
    }

}*/
