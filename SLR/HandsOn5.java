package examples.SLR;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;


public class HandsOn5 extends Agent {


	private Interfaz myGui;  //Objeto de mi interfaz
	

	
	protected void setup() {
	
	myGui = new Interfaz(this);
		
		// Add the behaviour serving queries from buyer agents
		//comportamiento
		addBehaviour(new RegresionLineal());
		
	}
	
	private class RegresionLineal extends OneShotBehaviour{
	
	public void action(){
	
	Regresion_Lineal op = new Regresion_Lineal();
	op.imprimirRegresionAnalitica();
	Regresion_Lineal_Gradiente op1 = new Regresion_Lineal_Gradiente();
	op1.imprimirRegresionGradiente();   //imprimir
	 
	
	 myGui.showGui();
		}//Termina action
	} // Termina clase RegresionLineal extends OneShotBehaviour
	
	
	public void CalculaRegresion()
	{
		Regresion_Lineal ob1 = new Regresion_Lineal();
		ob1.CalculaRegresionAnalitica(myGui.numero);
		Regresion_Lineal_Gradiente ob2 = new Regresion_Lineal_Gradiente();
		ob2.CalculaRegresionGradiente(myGui.numero);
		System.out.println("");
	
	}
	
	
	public class Dataset
	{
		private int[] x = {23,26,30,34,43,48,52,57,58};
		private int[] y = {651,762,856,1063,1190,1298,1421,1440,1518};
		
		
			public int[] GetX()
			{
				return x;
			}
			public int[] GetY()
			{
				return y;
			}
					
	}
	
	
	public class Regresion_Lineal extends Dataset 
	{
		private int sum_xy = 0, sum_x = 0, sum_y = 0, sum_x2 = 0;  
		private double beta0, beta1;
									
			public void CalcularSumatorias()
			{
       			 for(int actual = 0; actual < 9; actual++)
       		 	{
           		 	sum_xy += GetX()[actual] * GetY()[actual];
            		 	sum_x  += GetX()[actual];
            		 	sum_y  += GetY()[actual];
           		 	sum_x2 += Math.pow(GetX()[actual], 2);
       		 	}
			}		
		
			public void CalcularBeta1_Beta0()
			{
				System.out.println("SOLUCION ANALITICA:");
				System.out.println("");
        			System.out.println("Calculando valor de beta0 (Punto de Corte)");				
				beta0 = (double)(((sum_y * sum_x2) - (sum_x * sum_xy))/ ((9 * sum_x2) - Math.pow(sum_x, 2)));

				System.out.println("Listo! El valor de beta0 es: " + beta0);
        
				System.out.println("Calculando valor de beta1 (Pendiente)");

				beta1 = (double)(((9 * sum_xy) - (sum_x * sum_y)) / ((9 * sum_x2) - Math.pow(sum_x,2)));   
        			System.out.println("Listo! El valor de beta1 es: " + beta1);
        			System.out.println("");
			}
			
			public void Beta1_Beta0()
			{
				beta0 = (double)(((sum_y * sum_x2) - (sum_x * sum_xy))/ ((9 * sum_x2) - Math.pow(sum_x, 2)));
				beta1 = (double)(((9 * sum_xy) - (sum_x * sum_y)) / ((9 * sum_x2) - Math.pow(sum_x,2)));   
			}
						
			public void CalculaRegresionAnalitica(int _numero)
			{
				CalcularSumatorias();
				Beta1_Beta0();
				System.out.println("Calculado con Valores funcion Analitica");
				System.out.println("Para el Valor " +_numero+" Vale " + (beta0+(beta1*_numero)));	
			}		
		
			public void imprimirRegresionAnalitica()
			{
				CalcularSumatorias();
				CalcularBeta1_Beta0();
			}
			
	}  // End of inner class Regresion_Lineal
	






	private class Regresion_Lineal_Gradiente extends Regresion_Lineal
	{
		private double m=9, error=0,hipotesis=0,iters=1000000;
		private double a_deriv,b_deriv;
		private double _a=1,_b=1,_alpha=0.001;
		private double iter=1e-2;
		
			public double coste(int _x[], int _y[], double _a, double _b)
			{
		
				double errorcoste=0;
				double hipotesiss=0;


				for (int i=0; i<9; i++)
				{
					hipotesiss = _a+_b*_x[i];
					errorcoste += (_y[i] - hipotesiss)*(_y[i] - hipotesiss);
					errorcoste = errorcoste / (2*m);
				}
		
				return errorcoste / (2*m);
			}
			
			public void beta0_beta1()
			{
				for (int i=0; i<iters; i++)
				{
					b_deriv=0;
					a_deriv=0;
			
					for (int j=0; j<9; j++)
					{
						hipotesis = _a+_b*GetX()[j];
						a_deriv += hipotesis - GetY()[j];
						b_deriv += (hipotesis - GetY()[j]) * GetX()[j];
						error=0;
						error= coste(GetX(), GetY(), _a, _b);   //GetX()
						
						if(error<iter)
						{
							break;
						}
					}
					
					_a -= (a_deriv / m) * _alpha;
					_b -= (b_deriv / m) * _alpha;
				
				}			
			}
	
			public void CalculaDescensoGradiente()
			{
				for (int i=0; i<iters; i++)
				{
					b_deriv=0;
					a_deriv=0;
			
					for (int j=0; j<9; j++)
					{
						hipotesis = _a+_b*GetX()[j];
						a_deriv += hipotesis - GetY()[j];
						b_deriv += (hipotesis - GetY()[j]) * GetX()[j];
						error=0;
						error= coste(GetX(), GetY(), _a, _b);   //GetX()
						
						if(error<iter)
						{
							break;
						}
					}
					
					_a -= (a_deriv / m) * _alpha;
					_b -= (b_deriv / m) * _alpha;
				
				}
	
			System.out.println("SOLUCION CON GRADIENTE DESCENDENTE:");
			System.out.println("");
			System.out.println("Listo! El valor de beta0 es: " + _a);
			System.out.println("Listo! El valor de beta1 es: " + _b);		
			System.out.println("Listo! El valor de error es: " + error);	
			System.out.println("");	
	
			}
			
			public void CalculaRegresionGradiente(int _numero)
			{
				beta0_beta1();
				System.out.println("Calculado con Valores funcion Gradiente");
				System.out.println("Para el Valor " +_numero+" Vale " + (_a+(_b*_numero)));	
			}
	
			public void imprimirRegresionGradiente()
			{
				
				CalculaDescensoGradiente();
			}
	
	}  // End of inner class Regresion_Lineal_Gradiente

}

