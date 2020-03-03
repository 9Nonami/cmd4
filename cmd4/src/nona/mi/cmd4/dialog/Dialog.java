package nona.mi.cmd4.dialog;

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

    public void reset() {
        id = 0;
        endDialog = false;
    }

}
