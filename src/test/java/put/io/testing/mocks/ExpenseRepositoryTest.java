package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import put.io.students.fancylibrary.database.FancyDatabase;
import put.io.students.fancylibrary.database.IFancyDatabase;
import put.io.students.fancylibrary.service.FancyService;

public class ExpenseRepositoryTest {
    private  ExpenseRepository expenseRepository;
    private List<Expense> expenses;


    @Test
    void loadExpenses() {
        FancyDatabase db=new FancyDatabase();
        expenseRepository=new ExpenseRepository(db);
        expenseRepository.loadExpenses();
        assertEquals(0,expenseRepository.getExpenses().size());
    }

    @Test
    void loadExpenses2(){
        IFancyDatabase mockedDB=mock(IFancyDatabase.class);
        InOrder inOrder=inOrder(mockedDB);

        when(mockedDB.queryAll()).thenReturn(Collections.emptyList());

        expenseRepository=new ExpenseRepository(mockedDB);
        expenseRepository.loadExpenses();

        inOrder.verify(mockedDB).connect();
        inOrder.verify(mockedDB).queryAll();
        inOrder.verify(mockedDB).close();
        assertEquals(0,expenseRepository.getExpenses().size());
    }

    @Test
    void saveExpenses(){
        IFancyDatabase mockedDB=mock(IFancyDatabase.class);
        InOrder inOrder=inOrder(mockedDB);

        when(mockedDB.queryAll()).thenReturn(Collections.emptyList());

        expenseRepository=new ExpenseRepository(mockedDB);
        IntStream.range(0,5).forEach(i->expenseRepository.addExpense(new Expense()));
        expenseRepository.saveExpenses();

        inOrder.verify(mockedDB).connect();
        inOrder.verify(mockedDB,times(5)).persist(any(Expense.class));
        inOrder.verify(mockedDB).close();


    }

}
