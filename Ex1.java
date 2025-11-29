package assignments.Ex1;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynomial function is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	/**
	 * Computes the f(x) value of the polynomial function at x.
	 * @param poly - polynomial function
	 * @param x - x value of a point
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		for(int i=0;i<poly.length;i++) {
			double c = Math.pow(x, i);
			ans += c*poly[i];
		}
		return ans;
	}


	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double f1 = f(p,x1);
		double x12 = (x1+x2)/2;
		double f12 = f(p,x12);
		if (Math.abs(f12)<eps) {return x12;}
		if(f12*f1<=0) {return root_rec(p, x1, x12, eps);}
		else {return root_rec(p, x12, x2, eps);}
	}


	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
	 * @param xx - an array of doubles representing the x values of the points
	 * @param yy -an array of doubles representing the y values of the points
	 * @return an array of doubles representing the coefficients of the polynom.
     *
     * The function takes two arrays of point, one array of x values and another array of y values,together representing
     * 2 to 3 points.
     * then the function finds the polynomial that goes through all the points and returns the polynomial in an array representation
     *
     * ===Pseudocode===
     * 1. input                                                             // the function takes as an input 2 double arrays, the arrays need to be equal and their length should be smaller than 4 and bigger than 1 (cannot be null)
     * 2. double [] ans = null;                                             // ans equals null
     * 3. int lx = xx.length;                                               // lx equals the length of the array xx
     * 4. int ly = yy.length;                                               // ly equals the length of the array yy
     * 5. if(xx!=null && yy!=null && lx==ly && lx>1 && lx<4)                // checks if the input is correct
     * 6.   double A =0.0                                                   // state a new double variable A, equals 0
     * 7.   double B = 0.0                                                  // state a new double variable B, equals 0
     * 8.   double C = 0.0                                                  // state a new double variable C, equals 0
     * 9.   if(lx == 2)                                                     // if lx equals 2 then:
     *          if(xx[0]-xx[1] == 0){throw exception}                       // if xx[0]-xx[1] throw exception, can't divide by zero
     * 10.       B = (yy[0]-yy[1])/(xx[0]-xx[1]);                           // B equals (y1-y2)/(x1-x2)
     * 11.       C = yy[0]-B*xx[0];                                         // C equals y1-*Bx1
     * 12.  else                                                            //else (if lx doesn't equal 2)
     * 13.       double x1 = xx[0], x2 = xx[1], x3 = xx[2];                 // define x1 to be xx[0] and the on with all the x values in xx
     * 14.       double y1 = yy[0], y2 = yy[1], y3 = yy[2];                 // define y1 to be y[0] and the on with all the y values in yy
     * 15.       double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);          //calculate the denom
     *           if(denom == 0){throw exception}                            //if denom equals zero throw exception, can't divide by zero
     * 16.       A = x3(y2 - y1) + x2(y1 - y3) + x1 (y3 - y2)/denom         // A equals calculations based on the link given above
     * 17.       B = x3^2(y1 - y2) + x2^2(y3 - y1) + x1^2(y2 - y3)/denom    // B equals calculations based on the link given above
     * 18.       C = x2x3(x2-x3)y1 + x3x1(x3-x1)y2 + x1x2(x1-x2)y3/denom    // C equals calculations based on the link given above
     * 19.       if(A<EPS && B<EPS)                                        // if A and B are really small
     * 20.          ans = new double[]{C};                                  // ans equals an array of doubles containing C
     * 21.       else if(A<EPS)                                             // if A is really small
     * 22.          ans = new double[]{C,B};                                // ans equals an array of doubles containing C,B
     * 23.       else                                                       // else(if no one is really small)
     * 24.          ans = new double[]{C,B,A};                              // ans equals an array of doubles containing C,B,A
     * 25. return ans                                                       // return ans
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy) {
		double [] ans = null;
		int lx = xx.length;
		int ly = yy.length;
		if(xx!=null && yy!=null && lx==ly && lx>1 && lx<4) {
            /** add you code below*/
            double A =0.0;
            double B = 0.0;
            double C = 0.0;
            if(lx == 2){
                if(xx[0]-xx[1] == 0){
                    throw new IllegalArgumentException("invalid input");
                }
                B = (yy[0]-yy[1])/(xx[0]-xx[1]);
                C = yy[0]-B*xx[0];
            }else {
                double x1 = xx[0], x2 = xx[1], x3 = xx[2];
                double y1 = yy[0], y2 = yy[1], y3 = yy[2];
                double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
                if(denom == 0){
                    throw new IllegalArgumentException("invalid input");
                }
                A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
                B = (Math.pow(x3, 2) * (y1 - y2) + Math.pow(x2, 2) * (y3 - y1) + Math.pow(x1, 2) * (y2 - y3)) / denom;
                C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;
                if(A<EPS && B<EPS){
                    ans = new double[]{C};
                }
                else if(A<EPS){
                    ans = new double[]{C,B};
                }else{
                    ans = new double[]{C,B,A};
                }
            }
		///////////////////*/
		}
		return ans;
	}


	/** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true if p1 represents the same polynomial function as p2.
     *
     * The function take two double arrays and checks for n+1 values of x if both the polynomials represented by the arrays are equal(they have to be equal in all the x values)
     * The function randomizes the x values to make sure there isn't a coincidence
     *
     * ====pseudocode====
     * 1. input                                                         //the function take as an input two doubles arrays, each one of them representing a polynomial
     * 2.boolean ans = true                                             //boolean equals true
     * 3. if(p1.length !=0 && p2.length != 0)                           //if the array aren't empty
     * 4.   int max = Math.max(p1.length, p2.length);                   //max equals the length of the array with the higher length (if they are equal they are most likely to be of the same length, but just to make sure because there can be a really small number)
     * 5.   double check = Math.random() * (-10);                       //check equals a random number between 0 and -10
     * 6.   while (max != 0)                                            //while max isn't equal to zero
     * 7.       if (Math.abs(f(p1, check) - f(p2, check)) > EPS)        //if the absolute value of the polynomials is bigger than EPS
     * 8.           return false;                                       //return false
     * 9.       double random = Math.random() * 10;                     // random equals a number between 0 and 10
     * 10.      max = max - 1;                                          // decrease the value of max by 1
     * 11.      check = check + random;                                 // increment check by random
     * 12. return ans                                                   //return ans
	 */
	public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true;
        /** add you code below*/
        if(p1 == null || p2 == null){
            throw new IllegalArgumentException("invalid input");
        }
        if(p1.length !=0 && p2.length != 0) {
            int max = Math.max(p1.length, p2.length);

            double check = Math.random() * (-10);
            while (max != 0) {
                if (Math.abs(f(p1, check) - f(p2, check)) > EPS) {
                    return false;
                }
                double random = Math.random() * 10;
                max = max - 1;
                check = check + random;
            }
        }
        ///////////////////
		return ans;
	}


	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
     *
     * The function is given an array of doubles that represents a polynomial function
     * and returns a string that represents the polynomial function
     *
     * ====pseudocode====
     * 1.input                                                                  //the function input is an array of doubles
     * 2.ans = "";                                                              //ans is an empty string
     * 3.int count = poly.length-1;                                             //count equals the length of the array -1
     * 4.if(poly.length==0)                                                     //if the length of the array equals 0
     * 5.   ans="0";                                                            //ans equals "0"
     * 6.else                                                                   //else(if the length of the array isn't zero)
     * 7.   for(int i=0;i<poly.length;i++)                                      // loop while i is lower then poly length, each loop complete increment i by 1
     * 8.       if(poly[count-i] != 0)                                          // if the array at index count-i doesn't equal zero
     * 9.           if(count-i==1)                                              // if count-i equals 1
     * 10.              if(i != 0 && poly[count-i]>0)                           // if i isn't zero and the array at index count-i is bigger then one
     * 11.                  ans = ans + ("+")                                   // add to ans "+"
     * 12.              ans = ans + (poly[count-i] + "x ")                      // add to ans the value of the array at index count-i and "x" after
     * 13.          else if(i==0)                                               //else if i equals 0
     * 14               ans = ans + (poly[count-i] + "x^" + (count-i) + " ")    // add to ans x^count-i with a space at the end
     * 15.          else if(count-i == 0)                                       // else if count -i equals zero
     * 16.              if(poly[count-i]>0)                                     // if the value of the array at index count-i is bigger the zero
     * 17.                   ans = ans + ("+")                                  //add to ans "+"
     * 18.               ans = ans +(poly[count-i])                             //add to ans value of the array at index count-i
     * 19.          else                                                        //else
     * 20.              if(poly[poly.length-i-1]>0)                             //if value of the array at index count-i-1 is bigger then zero
     * 21.                  ans = ans + ("+")                                   // add to ans "+"
     * 22.              ans = ans + (poly[count-i] + "x^" + (count-i) + " ")    // add to ans value of the array at index count-i and x^count-i with a space at the end
     * 23.return ans                                                            // return ans
	 */
	public static String poly(double[] poly) {
		String ans = "";
        int count = poly.length-1;
		if(poly.length==0) {
            ans="0";
        }
		else {
            /** add you code below*/
             for(int i=0;i<poly.length;i++){
                 if(poly[count-i] != 0) {
                     if(count-i==1){
                         if(i != 0 && poly[count-i]>0){
                             ans = ans + ("+");
                         }
                         ans = ans + (poly[count-i] + "x ");
                     }
                     else if(i==0){
                         ans = ans + (poly[count-i] + "x^" + (count-i) + " ");
                     }
                     else if(count-i == 0) {
                         if(poly[count-i]>0){
                             ans = ans + ("+");
                         }
                         ans = ans +(poly[count-i]);
                     } else{
                         if(poly[poly.length-i-1]>0){
                             ans = ans + ("+");
                         }
                         ans = ans + (poly[count-i] + "x^" + (count-i) + " ");
                     }
                 }
             }
             ///////////////////
		}
		return ans;
	}


	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
     *
     * The function is given two double array representing polynomial functions and a two x values representing a range and
     * an eps value that determines when it is close enough.The function return the x value for which the two polynomial functions have an
     * intersection point
     *
     * ====pseudocode====
     * 1.input                                                                          //the function takes as an input two double arrays and three doubles
     * 2.double ans = x1;                                                               // ans equals x1
     * 3.double base_one = (f(p1, x1) - f(p2, x1));                                     // base_one equals the values of p1 at x2 minus the value of p2 at x2
     * 4.double base_two = (f(p1, x2) - f(p2, x2));                                     // base_two equals the values of p1 at x2 minus the value of p2 at x2
     * 5.if(base_one * base_two < 0)                                                    // if base_one multiplied by base_two is negative
     * 7.   x = (x1 + x2) / 2;                                                          // x is equals the value between x1 and x2(average)
     * 8.   fx1 = f(p1, x);                                                             // fx1 is the value of p1 at x
     * 9.   fx2 = f(p2, x);                                                             //fx2 is the value of p2 at x
     * 10.  if (|(fx1 - fx2)| < eps)                                                    // if the absolute value of fx1 minus fx2 is lower than eps
     * 11.      ans = x                                                                 // ans equals x
     * 13.  else if ((fx1 - fx2) * (f(p1, x1) - f(p2, x1)) > 0)                         //else if (fx1 minus fx2) multiplied (by the value of p1 at x1 minus the value of p2 at x1)
     * 14.      return sameValue(p1, p2, x, x2, eps)                                    // call this function again but instead of x1 give x
     * 15.  else                                                                        //else
     * 16.      return sameValue(p1, p2, x1, x, eps)                                    // call this function again but instead of x2 give x
     * 17.else                                                                          //else
     * 18.  throw Exception("the polynomials don't have an intersection point")         //throw an exception error
     * 19.return ans                                                                    /return ans
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = x1;
        /** add you code below*/
        double base_one = (f(p1, x1) - f(p2, x1));
        double base_two = (f(p1, x2) - f(p2, x2));
        if(base_one * base_two < 0) {
            double x = (x1 + x2) / 2;
            double fx1 = f(p1, x);
            double fx2 = f(p2, x);
            if (Math.abs(fx1 - fx2) < eps) {
                ans = x;
            } else if ((fx1 - fx2) * (f(p1, x1) - f(p2, x1)) > 0) {
                return sameValue(p1, p2, x, x2, eps);
            } else {
                return sameValue(p1, p2, x1, x, eps);
            }
        }else{
            throw new IllegalArgumentException("the polynomials don't have an intersection point");
        }
         ///////////////////
		return ans;
	}


	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
     *
     * The function gets an array of doubles representing a polynomial, two doubles representing x values and an integer representing the number of segment that the length will be divided into.
     * The function return the length of the polynomial within the given x values
     *
     * ====pseudocode====
     * 1.input                                                                  // the functin takes an input of one array of doubls 2, 2 doubles and one integer
     * 2.ans = x1;                                                              // ans equals x1
     * 3.space = (x2-x1)/numberOfSegments;                                      // space equals x2 minus x1 all divided by the number of segments
     * 4.count =0;                                                              //count equals zero
     * 5.len =0;                                                                // len equals zero
     * 6.while(count != numberOfSegments)                                       // while count isn't equal to the nubmer of segments loop
     * 7.     f1 = f(p,x1+space*count)                                          // f1 is equal to the value of p at x= x1 + space* count
     * 8.     f_len = f(p,x1+space*(count+1))                                   //f_len equals to the value of p at x= x1x1 + space* (count+1)
     * 9.     len += Math.sqrt(Math.pow(f_len-f1,2)+Math.pow(space,2))          // len is incremented by the square root of f_len^2 + space^2
     * 10.    count++                                                           //count is incremented by 1
     * 11.ans = len                                                             // ans equals len
     * 12.return ans                                                            //return ans
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments) {
		double ans = x1;
        /** add you code below*/
        double space = (x2-x1)/numberOfSegments;
        int count =0;
        double len =0;
        while(count != numberOfSegments) {
            double f1 = f(p,x1+space*count);
            double f_len = f(p,x1+space*(count+1));
            len += Math.sqrt(Math.pow(f_len-f1,2)+Math.pow(space,2));
            count++;
        }
        ans = len;
         ///////////////////
		return ans;
	}


	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
     *
     * The function is given two arrays of double that represent polynomial functions, two doubles that represent x values and one int that represents the number trapezoids.
     * Then the function splits the range of the x values to the number of trapezoids given and calculates each area and sums it's all to get the total area between the two functnion within the given range
     * There is a special case that the polynomials have an intersection point and then we need to calculate triangels instead of trapezoids
     * The function return the area between the polynomials in the given range
     *
     * ====pseudocode====
     * 1.input                                                                          // The function gets as an input two arrays of doubles, two doubles and one integer
     * 2.ans = 0;                                                                       // ans equals zero
     * 3.space = Math.abs(x2-x1)/numberOfTrapezoid;                                     // space equals |x2-x1| divided by the number of trapezoids
     * 4.count =0;                                                                      // count equals zero
     * 5.sum =0;                                                                        // sum equals zero
     * 6.   while(count != numberOfTrapezoid)                                           // loop while count isn't equal number of trapezoids
     * 7.      funcOne_1 = f(p1, x1);                                                   // funcOne_1 is equal the value of p1 at x1
     * 8.      funcOne_2 = f(p1, x1 + space);                                           // funcOne_2 is equal the value of p1 at x2
     * 9.      funcTwo_1 = f(p2, x1);                                                   // funcTwo_1 is equal the value of p2 at x1
     * 10.     funcTwo_2 = f(p2, x1 + space);                                           // funcTwo_2 is equal the value of p2 at x2
     * 11.     base_one = (funcOne_1 - funcTwo_1);                                      // base_one is equal funcOne_1 minus funcTwo_1
     * 12.     base_two = (funcOne_2 - funcTwo_2);                                      // base_two is equal funcOne_2 minus funcTwo_2
     * 13.         if (base_one * base_two < 0)                                         // if base_one multiplied by base_two is negative( can be said as if the polynomials have an intersection point)
     * 14.            base_one = Math.abs(base_one);                                    // base_one equals |base_one|
     * 15.            base_two = Math.abs(base_two);                                    // base_two equals |base_two|
     * 16.            intersection = sameValue(p1, p2, x1, x1+space, EPS);              // intersection is equal to sameValue(p1,p2,x1,x1+space,EPS) - sameValue find where in the range x1, x1+range p1 and p2 have an intersection point
     * 17.            triangel_one = (base_one*Math.abs(x1-intersection))/2;            // triangle_ one is equal to base_one multiplied by |x1 minus intersection| all this divided by 2
     * 18.            triangel_two = (base_two*Math.abs(x1+space-intersection))/2;      // triangle_ two is equal to base_two multiplied by |x1 plus space minus intersection| all this divided by 2
     * 19.            sum += triangel_one + triangel_two;                               // sum is incremented by triangle_one + triangle_two
     * 20.            count++;                                                          // count is incremented by 1
     * 21.            x1= x1+space;                                                     // x1 is incremented by space
     * 22.          else                                                                // else (the polynomials don't have an intersection point whiting the range x1 , x1+space)
     * 23.             base_one = Math.abs(base_one);                                   // base_one equals |base_one|
     * 24.             base_two = Math.abs(base_two);                                   // base_two equals |base_two|
     * 25.             sum += ((base_two + base_one) * space) / 2;                      // sum is incremented by the area of the trapezoid, ((base_two+base_one)*space)/2
     * 26.             count++;                                                         // count is incremented by 1
     * 27.             x1 = x1 + space                                                  // x1 in incremented by space
     * 28.      ans = sum;                                                              // ans equals sum
     * 29.return ans                                                                    // return ans
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        /** add you code below*/
        double space = Math.abs(x2-x1)/numberOfTrapezoid;
        int count =0;
        double sum =0;
        while(count != numberOfTrapezoid) {
            double funcOne_1 = f(p1, x1);
            double funcOne_2 = f(p1, x1 + space);
            double funcTwo_1 = f(p2, x1);
            double funcTwo_2 = f(p2, x1 + space);
            double base_one = (funcOne_1 - funcTwo_1);
            double base_two = (funcOne_2 - funcTwo_2);
            if (base_one * base_two < 0) {
                base_one = Math.abs(base_one);
                base_two = Math.abs(base_two);
                double intersection = sameValue(p1, p2, x1, x1+space, EPS);
                double triangel_one = (base_one*Math.abs(x1-intersection))/2;
                double triangel_two = (base_two*Math.abs(x1+space-intersection))/2;
                sum += triangel_one + triangel_two;
                count++;
                x1= x1+space;
            } else {
                base_one = Math.abs(base_one);
                base_two = Math.abs(base_two);
                sum += ((base_two + base_one) * space) / 2;

                count++;
                x1 = x1 + space;
            }
        }
        ans = sum;
         /////////////////// */
		return ans;
	}


	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return - an array of doubles representing the polynomial function of the string
     *
     * The function gets a string that represent a polynomial
     * and returns an array of doubles that represent the polynomial
     *
     * ====pseudocode====
     * 1.input                                                                              //the function get as an input one string
     * 2.double [] ans = ZERO                                                               //ans is an array of doubles the equals ZERO (The zero polynomial function is represented as an array with a single (0) entry)
     * 3.if(!p.isEmpty())                                                                   //if p isn't empty
     * 4.   String all = "0123456789^+-.x "                                                 //all is equal to all the possible values that can be in the string(used for checking valid input)
     * 5.   String start = "0123456789-x"                                                   //start is equal to all the possible values that can be in the first letter of the string(used for checking valid input)
     * 6.   String last = "0123456789x"                                                     //last is equal to all the possible values that can be in the last value of the string(used for checking valid input)
     * 7.   int falses = 0                                                                  //falses is equal to zero
     * 8.   for(int i =0; i < p.length();i++)                                               //loop while i is less the length of p, after each loop increment the value of i by 1
     * 9.       char character = p.charAt(i)                                                //character is the character at p on index i
     * 10.      if(i==0)                                                                    // if i is equal to zero
     * 11.           if(start.indexOf(character) == -1)                                     //if character isn't found in start
     * 12.              falses = falses+1;                                                  //increment falses by 1
     * 13.      else if(i==p.length()-1)                                                    //else if i is equal the length of p minus 1
     * 14.            if(last.indexOf(character) == -1)                                     //if character isn't found in  last
     * 15.              falses = falses+1;                                                  //increment falses by 1
     * 16.      else                                                                        //else
     * 17.           if(all.indexOf(character) == -1)                                       //if character isn't found in all
     * 18.              falses = falses+1;                                                  //increment the value of falses by 1
     * 19.      if(falses !=0)                                                              //if falses isn't equal zero
     * 20.           throw new IllegalArgumentException("invalid input")                    //throw an illegal argument exception
     * 21.   int degree = 0                                                                 // degree is equal to 1
     * 22.   int index = p.indexOf("^")                                                     // index is equal to the index of ^ in p(find the first one)
     * 23.   if(index == -1)                                                                //if ^ isn't found in p
     * 24.      index = p.indexOf("x")                                                      //index is equal the index of x in p
     * 25.      if(index == -1)                                                             //if x isn't found in p
     * 26.          degree = 0                                                              //degree equal 0
     * 27.      degree = 1                                                                  //degree equal 1
     * 28.   degree = Character.getNumericValue(p.charAt(index+1))                          //degree is equal int of the index after index
     * 29.   p = p.replace("x", "")                                                         //replace x with "" in p
     * 30.   p = p.replace("+", "")                                                         //replace + with "" in p
     * 31.   String[] func = p.split(" ")                                                   //func is an array of string, each value in the array is what before " ", continues to the next each time
     * 32.   for (int i = 0; i < func.length; i++)                                          //loop while i is less than the length of func, increment the value of i by 1
     * 33.             int num = func[i].indexOf('^')                                       //num is equal to the of ^ in func
     * 34.            if (num != -1)                                                        //if num isn't equal -1
     * 35.                  func[i] = (func[i].substring(0, num))                           //func in the index i is equal to itself but from the start until ^ without the ^
     * 36.             else                                                                 //else (if num is equal -1, no ^ in func at the index of i)
     * 37.                 func[i] = func[i]                                                //func is equal itself(i wrote this just for the code to be more understandable)
     * 38.         double[] new_ans = new double[degree+1]                                  //new_ans is an array of doubles with the length of degree +1
     * 39.         if(func.length < new_ans.length)                                         //if the length of func is lower than the length of new_ans
     * 40.             for (int i = 0; i < func.length; i++)                                //loop while i is lower than the length of func, increment the value of i by 1 after each loop
     * 41.                 new_ans[new_ans.length - i - 1] = Double.parseDouble(func[i])    //new_ans the index of it's length -i-1 is equal to the double value of func at index i
     * 42.        else                                                                      //else(if the length of func isn't lower than the length of new_ans)
     * 43.             for (int i = 0; i < func.length; i++)                                //loop while i is lower the func length, after each loop increment the value of i by 1
     * 44.                 new_ans[func.length - i - 1] = Double.parseDouble(func[i])       //new_ans the index of it's length -i-1 is equal to the double value of func at index i
     * 45.         ans = new_ans                                                            //ans equals new ans
     * 46.return ans                                                                        //return ans
	 */
    public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        /** add you code below*/
        if(!p.isEmpty()) {
            String all = "0123456789^+-.x ";
            String start = "0123456789-x";
            String last = "0123456789x";
            int falses = 0;
            for(int i =0; i < p.length();i++){
                char character = p.charAt(i);
                if(i==0){
                    if(start.indexOf(character) == -1){
                        falses = falses+1;
                    }
                }else if(i==p.length()-1){
                    if(last.indexOf(character) == -1){
                        falses = falses+1;
                    }
                }else{
                    if(all.indexOf(character) == -1){
                        falses = falses+1;
                    }
                }
                if(falses !=0){
                    throw new IllegalArgumentException("invalid input");
                }
            }
            int degree = 0;
            int index = p.indexOf("^");
            if(index == -1) {
                index = p.indexOf("x");
                if(index == -1) {
                    degree = 0;
                }
                degree = 1;
            }
            degree = Character.getNumericValue(p.charAt(index+1));

            p = p.replace("x", "");
            p = p.replace("+", "");
            String[] func = p.split(" ");
            for (int i = 0; i < func.length; i++) {
                int num = func[i].indexOf('^');
                if (num != -1) {
                    func[i] = (func[i].substring(0, num));
                } else {
                    func[i] = func[i];
                }
            }
            double[] new_ans = new double[degree+1];
            if(func.length < new_ans.length) {
                for (int i = 0; i < func.length; i++) {
                    new_ans[new_ans.length - i - 1] = Double.parseDouble(func[i]);
                }
            }else {
                for (int i = 0; i < func.length; i++) {
                    new_ans[func.length - i - 1] = Double.parseDouble(func[i]);
                }
            }
            ans = new_ans;
        }
         ///////////////////
		return ans;
	}


	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
	 * @param p1 - an array of doubles representing a polynomial
	 * @param p2 - an array of doubles representing a polynomial
	 * @return - an array of doubles representing the sum of the two polynomials given
     *
     * The function get two arrays of doubles representing polynomial functions,
     * and returns the polynomial function equal to the sum of them
     *
     * ====pseudocode====
     * 1.input                                              //the function gets an input of two double arrays
     * 2.double [] ans = ZERO;                              //ans is an array of doubles the equals ZERO (The zero polynomial function is represented as an array with a single (0) entry)
     * 3.if(p1.length !=0 || p2.length !=0)                 //if the length of p1 or the length of p2 aren't equal to zero
     * 4.   double[] answer                                 //declare a variable answer that is an array of doubles
     * 5.   if(p1.length == p2.length)                      //if the length of p1 and p2 are equal
     * 6.       answer  = new double[p1.length]             //set the length of answer to the length of p1
     * 7.       for(int i=0;i<answer.length;i++)            //loop while i is lower then the length of answer, each loop increment the value of i by 1
     * 8.           answer[i] = p1[i] + p2[i];              //answer at index i is equal to p1 at the index i plus p2 at the index of i
     * 9.   else if(p1.length > p2.length)                  //else if the length of p1 is bigger than length of p2
     * 10.      answer  = new double[p1.length]             //set the length of answer to the length of p1
     * 11.      for(int i=0;i<p2.length;i++)                //loop while i is lower than the length of p2 and after each loop increment the value of i by 1
     * 12.          answer[i] = p1[i] + p2[i]               //answer at index i is equal to p1 at the index i plus p2 at the index of i
     * 13.      for(int i=p2.length;i<p1.length;i++)        //loop while i is lower than the length of p1 , increment the value of i by 1 after each loop, i start equal to the length of p2
     * 14.          answer[i] = p1[i]                       //answer at index i is equal to the value of p1 at index i
     * 15.  else                                            //else (the length of p2 is bigger than the length of p1)
     * 16.      answer  = new double[p2.length]             //set the length of answer to the length of p2
     * 17.      for(int i=0;i<p1.length;i++)                //loop while i is lower than the length of p1 and after each loop increment the value of i by 1
     * 18.          answer[i] = p1[i] + p2[i]               //answer at index i is equal to p1 at the index i plus p2 at the index of i
     * 19.      for(int i=p1.length;i<p2.length;i++)        //loop while i is lower than the length of p2 , increment the value of i by 1 after each loop, i start equal to the length of p1
     * 20.          answer[i] = p2[i]                       //answer at index i is equal to the value of p1 at index i
     * 21.  ans = answer                                    //ans equals answer
     * 22.return ans                                        //return ans
	 */
	public static double[] add(double[] p1, double[] p2) {
		double [] ans = ZERO;//
        /** add you code below*/
        if(p1.length !=0 || p2.length !=0) {
            double[] answer;
            if(p1.length == p2.length) {
                answer  = new double[p1.length];
                for(int i=0;i<answer.length;i++) {
                    answer[i] = p1[i] + p2[i];
                }
            }
            else if(p1.length > p2.length) {
                answer  = new double[p1.length];
                for(int i=0;i<p2.length;i++) {
                    answer[i] = p1[i] + p2[i];
                }
                for(int i=p2.length;i<p1.length;i++) {
                    answer[i] = p1[i];
                }
            }
            else{
                answer  = new double[p2.length];
                for(int i=0;i<p1.length;i++) {
                    answer[i] = p1[i] + p2[i];
                }
                for(int i=p1.length;i<p2.length;i++) {
                    answer[i] = p2[i];
                }
            }
            ans = answer;
        }
         /////////////////// */
		return ans;
	}


	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
	 * @param p1 - an array of doubles representing a polynomial
	 * @param p2 - an array of doubles representing a polynomial
	 * @return - an array of doubles representing the multiplication of two polynomials
     *
     * The function gets two arrays of doubles representing polynomials,
     * and returns an array of doubles representing the polynomial that is the multiplication of the two polynomials
     *
     * ====pseudocode====
     * 1.input                                      //the function get an input, two arrays of doubles
     * 2.double [] ans = ZERO;                      //ans is an array of doubles the equals ZERO (The zero polynomial function is represented as an array with a single (0) entry)
     * 3.if(p1.length !=0 && p2.length !=0)         //if the length p1 and the length of p2 aren't equal to zero
     * 4.   sum_len = p1.length+p2.length           //sum_len is equal to the length of p1 plus the length p2
     * 5.   double []  answer                       //declare a variable answer, an array of doubles
     * 6.   if(p1.length<3 || p2.length<3)          //if the length of p1 or the length of p2 are less than 3
     * 7.       answer  = new double[sum_len-1]     //set the length of answer to be sum_len-1
     * 8.   else                                    //else(if the length of p1 or p2 is higher than 3)
     * 9.       answer = new double[sum_len - 2]    //set the length of answer to be sum_len-2
     * 10.  for(int i=0;i<p1.length;i++)            //loop while i is lower than the length of p1, after each loop increment i by 1
     * 11.      for(int j=0;j<p2.length;j++)        //loop while j is lower than the length of p2, after each loop increment j by 1
     * 12.          answer[i+j] += p1[i]*p2[j]      //answer at the index of i+j is equal to the value of p1 at the index of i multiplied by the value of p2 at the index of j
     * 13.  ans = answer                            //ans is equal to answer
     * 14.return ans                                //return ans
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;//
        /** add you code below*/
        if(p1.length !=0 && p2.length !=0) {
            int sum_len = p1.length+p2.length;
            double []  answer;
            if(p1.length<3 || p2.length<3) {
                answer  = new double[sum_len-1];
            }else {
                answer = new double[sum_len - 2];
            }
            for(int i=0;i<p1.length;i++) {
                for(int j=0;j<p2.length;j++) {
                    answer[i+j] += p1[i]*p2[j];
                }
            }
            ans = answer;
        }
         /////////////////// */
		return ans;
	}


	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po - an array of doubles representing a polynomial function
	 * @return ans - an array of doubles representing teh derivative of the polynomial function
     *
     * The function gets an array of doubles representing a polynomial function
     * and return an array of doubles that represents the derivative of the polynomial
     *
     * ====pseudocode====
     * 1.input                                          //the function gets an input of one doubles array
     * 2.double [] ans = ZERO                           //ans is an array of doubles the equals ZERO (The zero polynomial function is represented as an array with a single (0) entry)
     * 3.if(po.length >1)                               //if the length of po is bigger than 1
     * 4.   double[] answer = new double[po.length-1]   //answer is an array of doubles with the length of po -1
     * 5.   for(int i=1;i<po.length;i++)                //loop while i is lower than the length of po, after each loop increment the value of i by 1, i starts equal to 1
     * 6.       answer[i-1] = po[i]*i                   //answer at the index of i-1 is equal to the value of po at the index of i multiplied by i
     * 7.   ans = answer                                //ans equals to answer
     * 8.return ans                                     //return ans
	 */
	public static double[] derivative (double[] po) {
		double [] ans = ZERO;//
        /** add you code below*/
        if(po.length >1){
            double[] answer = new double[po.length-1];
            for(int i=1;i<po.length;i++){
                answer[i-1] = po[i]*i;
            }
            ans = answer;
        }

         /////////////////// */
		return ans;
	}
}
