import java.util.*;

public class Network {

	int[] sizes = { 5, 5, 1 };
	int totalLayers = sizes.length;
	
	Matrix[] biases = new Matrix[totalLayers - 1];
	Matrix[] weights = new Matrix[totalLayers - 1];

	Network() {
		for (int i = 0; i < totalLayers - 1; i++) {
			Random rand = new Random();
			biases[i] = new Matrix(sizes[i + 1], 1);
			weights[i] = new Matrix(sizes[i + 1], sizes[i]);
			biases[i] = biases[i].applyFunc(p -> rand.nextGaussian());
			weights[i] = weights[i].applyFunc(p -> rand.nextGaussian());
		}
	}

	Matrix sigmoid(Matrix z) {
		return z.applyFunc(p -> 1 / (1 + Math.pow(Math.E, -p)));
	}
	
	Matrix feedForward(Matrix inp){
        Matrix a = new Matrix(inp);
        for(int i = 0; i < totalLayers - 1; i++){
            a = sigmoid(weights[i].matrixMult(a).matrixAdd(biases[i]));
        }
        return a;
    }
}
