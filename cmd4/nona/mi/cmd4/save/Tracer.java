package nona.mi.cmd4.save;

import nona.mi.cmd4.main.Game;
import nona.mi.cmd4.scene.Scene;

public class Tracer {

	private int[] traces;



	public Tracer() {
		traces = new int[Game.TOTAL_TRACE];
		setEmptyTraces();
	}

	public void setEmptyTraces() {
		for (int i = 0; i < traces.length; i++) {
			traces[i] = Scene.NO_SCENE;
		}
	}

	public void add(int trace) {
		for (int i = 0; i < (traces.length - 1); i++) {
    		traces[i] = traces[i + 1];
    	}
    	traces[traces.length - 1] = trace;
	}

	public String getTracesAsString() {
		String ini = "" + traces[0];
		for (int i = 1; i < traces.length; i++) {
			ini = ini + "," + traces[i];
		}
		return ini;
	}

	public int[] getTraces() {
		return traces;
	}

	public void setTraces(int[] traces) {
		this.traces = traces;
	}

}