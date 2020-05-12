package Board;

import Users.ParticipantsManager;

import static Utils.Logger.*;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ParticipantListPanel extends JPanel {

    private JScrollPane scrollPane = new JScrollPane();
    private JList<String> participants = new JList<>();
    
    private final JFrame PARENT;

    private String selectedUID;

    private ParticipantsManager participantsManager;


    public ParticipantListPanel(JFrame PARENT, ParticipantsManager participantsManager) {
        this.participantsManager = participantsManager;
        this.PARENT = PARENT;

//        setLayout(new BorderLayout());
//        initList();
//        participantsManager.setParticipantList(this);
//        updateList();
    }

    public void updateList() {
        log("updating list");

        Vector<String> list = new Vector<>();
        list.add("[Host] " + participantsManager.getServerId());

        for (String uid: participantsManager.getAllParticipants()) {
            list.add("[Participant] " + uid);
        }

        if(participantsManager.isHost()) {
            for (String uid: participantsManager.getAllWaiting()) {
                list.add("[Waiting] " + uid);
            }
        }

        participants.setListData(list);
    }

    public void initList() {
        log("init Participant List");
        scrollPane.setPreferredSize(new Dimension(200,200));

        participants.setForeground(Color.black);
        participants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel subPanel = new JPanel();

        participants.addListSelectionListener((e) -> {
            if (!participants.getValueIsAdjusting()) {
                String select = participants.getSelectedValue();

                if (select != null) {
                    // find the real username
                    String[] temp = participants.getSelectedValue().split("]");

                    if(temp[1] != null) {
                        selectedUID = temp[1];

                        if (participantsManager.getAllWaiting().contains(selectedUID)) {
                            int result = JOptionPane.showConfirmDialog(PARENT,
                                    "Allow |" + temp[1].strip() + "| in?",
                                    "Visitor Management",
                                    JOptionPane.YES_NO_CANCEL_OPTION);

                            switch (result) {
                                case (0) -> {
                                    // yes
                                    participantsManager.allowJoin(selectedUID);
                                }
                                case (1) -> {
                                    //No
                                    participantsManager.rejectJoin(selectedUID);
                                }
                                case (2) -> {
                                    //cancel
                                    log("Cancel");
                                }
                            }
                        } else {
                            int result = JOptionPane.showConfirmDialog(PARENT,
                                    "Kick |" + temp[1].strip() + "| ?",
                                    "Guest Management",
                                    JOptionPane.YES_NO_OPTION);

                            switch (result) {
                                case (0) -> {
                                    // yes
                                    participantsManager.kick(selectedUID);
                                }
                                case (1) -> {
                                    //No
                                    log("Not kicking");
                                }
                            }
                        }
                    }
                }
            }
        });

        subPanel.setLayout(new BorderLayout(0, 0));

        scrollPane.add(participants);
        scrollPane.setViewportView(participants);
        subPanel.add(scrollPane);
        add(subPanel, BorderLayout.CENTER);

    }

}