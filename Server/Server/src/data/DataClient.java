package data;

import com.corundumstudio.socketio.SocketIOClient;
import java.awt.Component;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JTable;
import swing.PanelStatus;

/**
 *
 * @author PC
 */
public class DataClient {

    public SocketIOClient getSocket() {
        return client;
    }

    public void setSocket(SocketIOClient client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public DataClient(SocketIOClient client, String name, JTable jtable) {
        this.client = client;
        this.name = name;
        this.status = new PanelStatus();
        this.table = table;
    }

    public DataClient() {
    }
    private SocketIOClient client;
    private String name;
    //Key integer is fileID, use hash to store multy transfer
    private final HashMap<Integer, DataWriter> list = new HashMap<>();
    private PanelStatus status;
    private JTable table;

    public void addWrite(DataWriter data, int fileID) {
        list.put(fileID, data);
        status.addItem(fileID, data.getFile().getName(), data.getMaxFileSize());
        autoRowHeight(table, 3);
    }

    public void writeFile(byte[] data, int fileID) throws IOException {
        list.get(fileID).writeFile(data);
        status.updateStatus(fileID, (int) list.get(fileID).getPercentage());
        table.repaint();
    }

    public void closeWriter(int fileID) throws IOException {
        list.get(fileID).close();
    }

    public Object[] toRowTable(int row) {
        return new Object[]{this, row, name};
    }

    private void autoRowHeight(JTable table, int... cols) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight();
            for (int col : cols) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                if (comp.getPreferredSize().height > rowHeight) {
                    rowHeight = comp.getPreferredSize().height;
                }
            }
            table.setRowHeight(row, rowHeight);
        }
    }

    /**
     * @return the status
     */
    public PanelStatus getStatus() {
        return status;
    }

}
