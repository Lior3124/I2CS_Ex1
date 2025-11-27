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
	 * @param x
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
                A =0.0;
                B = (xx[0]-xx[1])/(yy[0]-yy[1]);
                C = yy[0]-B*xx[0];
            }else {
                double x1 = xx[0], x2 = xx[1], x3 = xx[2];
                double y1 = yy[0], y2 = yy[1], y3 = yy[2];

                double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);

                if (Math.abs(denom) > Math.pow(10, 12) || Math.abs(denom) < Math.pow(10, -8)) {
                    A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2));
                    B = (Math.pow(x3, 2) * (y1 - y2) + Math.pow(x2, 2) * (y3 - y1) + Math.pow(x1, 2) * (y2 - y3));
                    C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3);
                    ans = new double[]{C ,B};
                } else {
                    A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
                    B = (Math.pow(x3, 2) * (y1 - y2) + Math.pow(x2, 2) * (y3 - y1) + Math.pow(x1, 2) * (y2 - y3)) / denom;
                    C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;
                    ans = new double[]{C ,B ,A};
                }
                if(A<EPS){
                    ans = new double[]{C ,B};
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
	 */
	public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true;
        /** add you code below*/
        if(p1.length !=0 && p2.length != 0) {
            int max = Math.max(p1.length, p2.length);
            max = max;

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
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = x1;
        /** add you code below*/
        boolean found = false;
        double x = (x1 + x2) / 2;
        double fx1 = f(p1, x);
        double fx2 = f(p2, x);
        if (Math.abs(fx1 - fx2) < eps) {
            ans = x;
            found = true;
        }
        else if ((fx1 - fx2) * (f(p1, x1) - f(p2, x1)) > 0) {
            return sameValue(p1, p2, x, x2, eps);
        } else {
            return sameValue(p1, p2, x1, x, eps);
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
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return an array of doubles representing the polynomial function of the string
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        /** add you code below*/
        if(!p.isEmpty()) {
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
            double[] new_ans = new double[func.length];
            for (int i = 0; i < func.length; i++) {
                new_ans[func.length - i - 1] = Double.parseDouble(func[i]);
            }
            ans = new_ans;
        }
         ///////////////////
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
		double [] ans = ZERO;//
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;//
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po
	 * @return
	 */
	public static double[] derivative (double[] po) {
		double [] ans = ZERO;//
        /** add you code below*/


         /////////////////// */
		return ans;
	}
}
