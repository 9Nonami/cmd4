package nona.mi.cmd4.dialog;

import nona.mi.cmd4.save.Save;



public class Dialog {

    private String[] dg;
    private int id;
    private boolean endDialog;



    public Dialog(String[] dgs) {
        dg = dgs;
        id = 0;
        endDialog = false;
    }

    public void update() {
        if (id < dg.length - 1) {
            id++;
        } else {
            endDialog = true;
        }
    }

    public void render() {
        String temp = dg[id];
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '@') {
                System.out.println("");
                continue;
            }
            System.out.print(temp.charAt(i));
        }
        System.out.println("");
    }

    public boolean isEndDialog() {
        return endDialog;
    }

    public String getDialogForSave() {
    	if (dg != null) {
    		String s = dg[0];
    		if (s.contains("@")) {
    			s = s.replace('@', ' ');
    		}
    		if (s.length() > Save.SAVE_TEXT_OFFSET) {
    			return s.substring(0, Save.SAVE_TEXT_OFFSET);
    		} else {
    			return s;
    		}
    	}
    	return "Empty dialog.";
    }

    public void reset() {
        id = 0;
        endDialog = false;
    }

}
