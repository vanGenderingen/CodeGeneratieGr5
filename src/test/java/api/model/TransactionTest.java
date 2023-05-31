package groep5.api.model;

import io.swagger.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class TransactionTest {

    private Transaction createdTransaction;

    @Before
    public void setup() {
        createdTransaction = new Transaction();
    }

    @Test
    public void createTransactionShouldNotBeNull() {
        assertNotNull(createdTransaction);
    }

}