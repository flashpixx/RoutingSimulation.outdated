/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason Gridworld                                      #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp.kraus@tu-clausthal.de)               #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package agentrouting;

import org.junit.Test;


/**
 * example jUnit test - the best-practice is, that for
 * each class within the src/main directory a test-class
 * exists under src/test with identical package structure,
 * but all jUnit test classes must use the prefix "Test-any class name-.java"
 * so that Maven can run it automatically, the test-class can be used more than
 * one method for testing, each test-method is run in parallel during the Maven
 * build process
 */
public final class TestExample
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
         */
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
         * any results, input values or complex tests,
         * we would fail this second test so we create manually the assert call with true,
         * assertTrue fails iif the parameter is "false", there exists a lot of different asserts
         * see http://junit.sourceforge.net/javadoc/org/junit/Assert.html
         * if the test fails, the Maven build process is break-down
         * assertTrue(false);
         */
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
        final TestExample l_test = new TestExample();

        l_test.first();
        l_test.second();
    }

}
