package assignments.Ex1;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};

    @Test
    /**
     * Tests the roots of a few polynomials
     */
    void testRoot_rec(){
        double expected_one = 3.19582;
        double actual_one = Ex1.root_rec(P1,2,4,Ex1.EPS);

        double expected_two = -1.0;
        double actual_two = Ex1.root_rec(po1,-2,4,Ex1.EPS);

        double expected_three = -5.68741;
        double actual_three = Ex1.root_rec(po2,-8,0,Ex1.EPS);

        assertEquals(expected_one,actual_one,Ex1.EPS);
        assertEquals(expected_two,actual_two,Ex1.EPS);
        assertEquals(expected_three,actual_three,Ex1.EPS);
    }


    @Test
    /**
     * Tests the conversion of arr representing a polynom to string
     * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
     */
    void testPoly() {
        String expected_one = "2.0x +2.0";
        String actual_one = Ex1.poly(po1);

        String expected_two = "0.2x^2 +0.61x -3.0";
        String actual_two = Ex1.poly(po2);

        String expected_three = "0.02x^4 -0.02x^3 -0.7x^2 +1.0x +2.0";
        String actual_three = Ex1.poly(po3);

        String expected_four = "0.2x^2 +0.61x -3.0";
        String actual_four = Ex1.poly(po4);

        assertEquals(expected_one, actual_one,"You have a problem with poly func");
        assertEquals(expected_two, actual_two,"You have a problem with poly func");
        assertEquals(expected_three, actual_three,"You have a problem with poly func");
        assertEquals(expected_four, actual_four,"You have a problem with poly func");
    }


    @Test
    /**
     * Tests The convertion and calculation of 3 or 2 point into a polynom
     */
    void testPolynomFromPoints(){
        double [] x_values = {1.2,-15,7};
        double [] y_values = {Ex1.f(po1,1.2),Ex1.f(po1,-15),Ex1.f(po1,7)};
        double [] actual_one = Ex1.PolynomFromPoints(x_values,y_values);
        assertArrayEquals(po1,actual_one,Ex1.EPS);

        double [] y_values_two = {Ex1.f(po2,1.2),Ex1.f(po2,-15),Ex1.f(po2,7)};
        double [] actual_two = Ex1.PolynomFromPoints(x_values,y_values_two);
        assertArrayEquals(po2,actual_two,Ex1.EPS);

        double [] y_values_three = {2,2,2};
        double [] x_values_three = {3,4,5,};
        double [] actual_three = Ex1.PolynomFromPoints(x_values_three,y_values_three);
        double [] expected_three = {2};
        assertArrayEquals(expected_three,actual_three,Ex1.EPS);

        double [] x_values_four = {Ex1.EPS,0.0000000001,0.000000007};
        double [] y_values_four = {Ex1.f(po1,Ex1.EPS),Ex1.f(po1,0.0000000001),Ex1.f(po1,0.000000007)};
        double [] actual_four = Ex1.PolynomFromPoints(x_values_four,y_values_four);
        assertArrayEquals(po1,actual_four,Ex1.EPS);


    }


    @Test
    /**
     * Tests The convertion and calculation of 3 or 2 point into a polynom, but the input is invalid
     */
    public void testPolynomFromPoints2(){
        double[] y = {1,2,3};
        double[] y2 ={1,2};
        double[] x1 = {1,1,1};
        double[] x2 = {1,1};
        assertThrows(IllegalArgumentException.class, () -> Ex1.PolynomFromPoints(x1,y));
        assertThrows(IllegalArgumentException.class, () -> Ex1.PolynomFromPoints(x2,y2));
        assertEquals(null,Ex1.PolynomFromPoints(x1,y2));
    }

    @Test
    /**
     * Tests the length of a function between two points
     */
    void testlength() {

        double expected_one = 5*Math.sqrt(5);   // calculated beforehand with an integral
        double actual_one = Ex1.length(po1,0,5,1000);

        double expected_two = 9.612996805059;   // calculated beforehand with an integral
        double actual_two = Ex1.length(po2,5,0,1000);

        double expected_three = 7.29263;    // calculated beforehand with an integral
        double actual_three = Ex1.length(po3,2,-2,1000);

        double expected_four = 19.7253393796548;    // calculated beforehand with an integral
        double actual_four = Ex1.length(po4,-7,5,100000);

        assertEquals(expected_one,actual_one,Ex1.EPS);
        assertEquals(expected_two,actual_two,Ex1.EPS);
        assertEquals(expected_three,actual_three,Ex1.EPS);
        assertEquals(expected_four,actual_four,Ex1.EPS);
    }


 	@Test
	/**
	 * Tests that f(x) == poly(x).
	 */
	void testF() {
		double fx0 = Ex1.f(po1, 0);
		double fx1 = Ex1.f(po1, 1);
		double fx2 = Ex1.f(po1, 2);
		assertEquals(fx0, 2, Ex1.EPS);
		assertEquals(fx1, 4, Ex1.EPS);
		assertEquals(fx2, 6, Ex1.EPS);
	}


	@Test
	/**
	 * Tests that p1(x) + p2(x) == (p1+p2)(x)
	 */
	void testF2() {
		double x = Math.PI;
		double[] po12 = Ex1.add(po1, po2);
		double f1x = Ex1.f(po1, x);
		double f2x = Ex1.f(po2, x);
		double f12x = Ex1.f(po12, x);
		assertEquals(f1x + f2x, f12x, Ex1.EPS);
	}


	@Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p12 = Ex1.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex1.mul(po2, minus1);
		double[] p1 = Ex1.add(p12, pp2);
		assertTrue(Ex1.equals(p1, po1));
	}


	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double[] p12 = Ex1.add(po1, po2);
		double[] p21 = Ex1.add(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}


	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex1.add(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, po1));
	}


    @Test
    /**
     * Test that 0+0 = 0
     */
    void testAdd4() {
        double[] actual = Ex1.add(Ex1.ZERO, Ex1.ZERO);
        assertArrayEquals(Ex1.ZERO,actual);
    }

	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
		double[] p1 = Ex1.mul(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, Ex1.ZERO));
	}


	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
		double[] p12 = Ex1.mul(po1, po2);
		double[] p21 = Ex1.mul(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}


	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex1.mul(po1, po2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex1.f(po1, x);
			double f2x = Ex1.f(po2, x);
			double f12x = Ex1.f(p12, x);
			assertEquals(f12x, f1x*f2x, Ex1.EPS);
		}
	}


	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex1.derivative(p); // 2x + 6
		double[] dp2 = Ex1.derivative(dp1); // 2
		double[] dp3 = Ex1.derivative(dp2); // 0
		double[] dp4 = Ex1.derivative(dp3); // 0
		assertTrue(Ex1.equals(dp1, pt));
		assertTrue(Ex1.equals(Ex1.ZERO, dp3));
		assertTrue(Ex1.equals(dp4, dp3));
	}


    @Test
    /**
     * Test derivative if the polynomial has a max degree of 0 or 1
     */
    public void testDerivaitive2(){
        double [] actual_one = Ex1.derivative(Ex1.ZERO);

        double[] test = {1.0};
        double [] actual_two = Ex1.derivative(test);

        assertArrayEquals(Ex1.ZERO, actual_one);
        assertArrayEquals(Ex1.ZERO, actual_two);
    }


    @Test
    /**
     * Tests the derivative of polynomial that has a max degree of 2
     */
    public void testDerivaitive3(){
        double [] actual = Ex1.derivative(po1);
        double[] expected = {2.0};
        assertArrayEquals(expected, actual);
    }


	@Test
	/** 
	 * Tests the parsing of a polynom in a String like form.
	 */
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
		String sp2 = "3.1x^2 +2.3x -1.1";
		String sp = Ex1.poly(p);
		double[] p1 = Ex1.getPolynomFromString(sp);
		double[] p2 = Ex1.getPolynomFromString(sp2);
		boolean isSame1 = Ex1.equals(p1, p);
		boolean isSame2 = Ex1.equals(p2, p);
		if(!isSame1) {fail();}
		if(!isSame2) {fail();}
		assertEquals(sp, Ex1.poly(p1));
	}
    @Test
    /**
     * Tests the parsing of a polynom in a String like form, but with invalid input
     */
    public void testFromString2() {
        assertThrows(IllegalArgumentException.class, () -> Ex1.getPolynomFromString("a"));
        assertThrows(IllegalArgumentException.class, () -> Ex1.getPolynomFromString("2x^f"));
    }

    @Test
    public void testFromString3() {
        double [] expected_one = {0,0,1.0};
        String p = "1.0x^2";
        assertArrayEquals(expected_one, Ex1.getPolynomFromString(p));
    }




	@Test
	/**
	 * Tests the equality of pairs of arrays.
	 */
	public void testEquals() {
		double[][] d1 = {{0}, {1}, {1,2,0,0}};
		double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
		double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
		for(int i=0;i<d1.length;i=i+1) {
			assertTrue(Ex1.equals(d1[i], d2[i]));
		}
		for(int i=0;i<d1.length;i=i+1) {
			assertFalse(Ex1.equals(d1[i], xx[i]));
		}
	}

    @Test
    /**
     * Test the equality of pair of array, but with the array are null,supposed to return error
     */
    public void testEquals2() {
        double [] arr = {1};
        assertThrows(IllegalArgumentException.class, () -> Ex1.equals(arr, null));
        assertThrows(IllegalArgumentException.class, () -> Ex1.equals(null, arr));
    }


    @Test
    /**
     * Test the equality of pairs of array, but when both are null
     */
    public void testEquals3(){
        assertEquals(true, Ex1.equals(null,null));
    }


    @Test
    /**
     * Test when two polynomial have an intersection point
     */
    public void testSameValue() {
        double actual_one = Ex1.sameValue(po1,po2,-5.0,-2.0,Ex1.EPS);
        double expected_one = -2.61398; // calculated beforehand

        double actual_two = Ex1.sameValue(P1,po2,0,4,Ex1.EPS);
        double expected_two = 3.11852;

        assertEquals(expected_one, actual_one,Ex1.EPS);
        assertEquals(expected_two, actual_two,Ex1.EPS);


    }


    @Test
	/**
	 * Tests is the sameValue function is symmetric.
	 */
	public void testSameValue2() {
		double x1=-4, x2=0;
		double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
		double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
		assertEquals(rs1,rs2, Ex1.EPS);
	}


    @Test
    /**
     * Tests sameValue if there is no intersection point
     */
    public void testSameValue3(){
        assertThrows(IllegalArgumentException.class, () -> Ex1.sameValue(P1,po2,0,2,Ex1.EPS));
    }


    @Test
	/**
	 * Test the area function - it should be symmetric.
	 */
	public void testArea() {
		double x1=-4, x2=0;
		double a1 = Ex1.area(po1, po2, x1, x2, 100);
		double a2 = Ex1.area(po2, po1, x1, x2, 100);
		assertEquals(a1,a2, Ex1.EPS);
}


	@Test
	/**
	 * Test the area f1(x)=0, f2(x)=x;
	 */
	public void testArea2() {
		double[] po_a = Ex1.ZERO;
		double[] po_b = {0,1};
		double x1 = -1;
		double x2 = 2;
		double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
		double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
		double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
		double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
		double area =2.5;
		assertEquals(a1,area, Ex1.EPS);
		assertEquals(a2,area, Ex1.EPS);
		assertEquals(a3,area, Ex1.EPS);
		assertEquals(a100,area, Ex1.EPS);
	}


	@Test
	/**
	 * Test the area function.
	 */
	public void testArea3() {
		double[] po_a = {2,1,-0.7, -0.02,0.02};
		double[] po_b = {6, 0.1, -0.2};
		double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
		double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
		double area = 58.5658;
		assertEquals(a1,area, Ex1.EPS);
	}
}
