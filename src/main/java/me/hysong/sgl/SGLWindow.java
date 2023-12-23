package me.hysong.sgl;

import lombok.Getter;
import lombok.Setter;
import me.hysong.libhyextended.objects.dataobj2.DataObject2;
import me.hysong.sgl.panels.SGLPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class SGLWindow extends DataObject2 {

    private JFrame frame;
    private JPanel contentPane;

    private HashMap<String, JPanel> contents = new HashMap<>();
    private HashMap<String, SGLEntity> entities = new HashMap<>();

    public SGLWindow() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void load(Class<?> scene) {
        // Check if the scene is a SGLPanel
        // If so, then instantiate it. Once instantiated, it automatically sets the panel.
        if (SGLPanel.class.isAssignableFrom(scene)) {
            try {
                SGLPanel panel = (SGLPanel) scene.getConstructor(SGLWindow.class).newInstance(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setPanel(SGLPanel panel) {
        contentPane = panel.getPanel();
        frame.setContentPane(contentPane);
    }

    public void add(String name, JPanel panelToAdd) {
        try {
            contents.put(name, panelToAdd);
            this.getContentPane().add(panelToAdd);
        } catch (NullPointerException e) {
            throw new RuntimeException("Panel is not set! Please set the panel first.");
        }
    }

    public void addEntity(String name, SGLEntity e) {
        entities.put(name, e);
        add(name, e.getPanel());
    }

    public JPanel get(String name) {
        return contents.get(name);
    }

    public SGLEntity getEntity(String name) {
        return entities.get(name);
    }

    public void drop(String name) {
        try {
            contents.get(name).setVisible(false);
            this.getContentPane().remove(contents.get(name));
            contents.remove(name);
        } catch (NullPointerException e) {
            throw new RuntimeException("Panel is not set! Please set the panel first.");
        }
    }

    public void dropEntity(String name) {
        SGLEntity entity = entities.get(name);
        if (entity == null) {
            System.out.println("Entity <" + name + "> does not exist!");
            return;
        }
        entities.get(name).getPanel().setVisible(false);
        entities.remove(name);
        drop(name);
    }

    public ArrayList<SGLEntity> getOverlappingEntities(SGLEntity e) {
        ArrayList<SGLEntity> overlappingEntities = new ArrayList<>();
        for (SGLEntity entity : entities.values()) {
            if (entity.checkOverlapping(e)) {
                overlappingEntities.add(entity);
            }
        }
        return overlappingEntities;
    }

    public void start() {
        frame.setVisible(true);
    }
}
