package agentrouting;

import org.junit.Test;


/**
 * example jUnit test - the best-practice is, that for
 * each class within the src/main directory a test-class
 * exists under src/test with identical package structure,
 * but all jUnit test classes must use the prefix "Test<any class name>.java"
 * so that Maven can run it automatically, the test-class can be used more than
 * one method for testing, each test-method is run in parallel during the Maven
 * build process
 **/
public final class Test_CExample
{


    /**
     * this test-method is called by Maven
     * so it must use the annotation @Test
     **/
    @Test
    public void first()
    {
        /**
         * here can exists any test-code,
         * if no exception is thrown the test
         * ended successfully
         **/
    }

    /**
     * second test, which should fail
     **/
    @Test
    public void second()
    {
        /**
         * for creating manually test-results
         * we uses "asserts" (this structures is copied from the C development https://en.wikipedia.org/wiki/Assert.h)
         * and is part of jUnit in Java, in short:
         * An assert is a function, which gets a boolean as input if the boolean is true, the program execution is stopped
         * immediatly and we get an definied error message. Asserts will be used during the developing process, to check
         * any results, input values or complex tests
         **/

        // we would fail this second test so we create manually the assert call with true,
        // assertTrue fails iif the parameter is "false", there exists a lot of different asserts
        // see http://junit.sourceforge.net/javadoc/org/junit/Assert.html
        // if the test fails, the Maven build process is break-down
        //assertTrue(false);   
    }


    /**
     * it is recommand, that each test-class uses also
     * a main-method, which calls the test-methods manually,
     * because the Maven-test calls does not allow any debugging
     * with the IDE, so this main-method allows to start the
     * test through the IDE and run the IDE debugger
     **/
    public static void main( final String[] p_args )
    {
        final Test_CExample l_test = new Test_CExample();

        l_test.first();
        l_test.second();
    }

}
