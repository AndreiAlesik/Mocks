package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import put.io.students.fancylibrary.service.FancyService;

public class ExpenseManagerTest {
    private static ArrayList<Expense> expenses;
    @BeforeAll
    static void beforeAll() {
        expenses=new ArrayList<>();
        Expense expense=new Expense();
        expense.setAmount(3);
        IntStream.range(0,3).forEach(i->expenses.add(expense));
    }

    @Test
    void testCalculateTotal() {
        IExpenseRepository expenseRepositoryMocked=mock(IExpenseRepository.class);

        when(expenseRepositoryMocked.getExpenses()).thenReturn(expenses);

        FancyService fancyServiceMocked=mock(FancyService.class);

        ExpenseManager expenseManager=new ExpenseManager(expenseRepositoryMocked, fancyServiceMocked);

        InOrder inOrder=inOrder(expenseRepositoryMocked);
        assertEquals(9, expenseManager.calculateTotal());
    }

    @Test
    void testCalculateForCategory(){
        IExpenseRepository expenseRepositoryMocked=mock(IExpenseRepository.class);
        FancyService fancyServiceMocked=mock(FancyService.class);

        when(expenseRepositoryMocked.getExpensesByCategory(anyString())).thenReturn(Collections.emptyList());
        when(expenseRepositoryMocked.getExpensesByCategory("Home")).thenReturn(expenses);
        when(expenseRepositoryMocked.getExpensesByCategory("Car")).thenReturn(expenses);

        ExpenseManager expenseManager=new ExpenseManager(expenseRepositoryMocked,fancyServiceMocked);

        assertEquals(0,expenseManager.calculateTotalForCategory("Sport"));
        assertEquals(0,expenseManager.calculateTotalForCategory("Food"));
        assertEquals(9, expenseManager.calculateTotalForCategory("Car"));
        assertEquals(9,expenseManager.calculateTotalForCategory("Home"));
    }

    
}
