/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;

import file.cod;
import file.csv;

/**
 *
 * @author josue
 */
public class Open extends javax.swing.JFrame {

    /**
     * Creates new form Open
     */
    public Open() {
        initComponents();
    }
    
    public void Enter(){
        
        setVisible(true);
        setResizable(false);
        
        setTitle("Abrir CSV");
        setLocation(Location.x,Location.y);
        
    }
    
    private void Exc(String tct){
        
        dispose();
        Index into = new Index();
        into.Enter(tct);
        
    }
    
    private void Exportar(String diretory, String folder){
        
        cod dar = new cod();
        
        csv cmd = new csv(diretory.replace(".csv",""));
        
        if(cmd.Tot() >= 0){
            
            boolean YouTube = false;
            
            int i = 0;
            
            do{
                
                if(cmd.Tot(i) >= 4){
                    
                    boolean title_a = !dar.Link(cmd.Read(i, 0));
                    boolean title_b = !cmd.Read(i, 0).trim().isBlank();
                    boolean vcr_a = !dar.Link(cmd.Read(i, 1));
                    boolean vcr_b = !cmd.Read(i, 1).trim().isBlank();
                    boolean lnk = dar.Link(cmd.Read(i, 2));
                    boolean vch_a = !dar.Link(cmd.Read(i, 3));
                    boolean vch_b = !cmd.Read(i, 3).trim().isBlank();
                    boolean lch = dar.Link(cmd.Read(i, 4));
                    
                    YouTube = title_a && title_b && vcr_a && vcr_b && lnk && vch_a && vch_b && lch;
                    
                }//if(cmd.Tot(i) >= 4)
                
                i++;
                
            }while(YouTube && i < cmd.Tot());
            
            dispose();
            
            if(YouTube){
                
                Tube youtube = new Tube();
                youtube.Enter(cmd);
                
            } else {
                
                Text txt = new Text();
                txt.Enter(cmd);
                
            }//if(YouTube)
            
        } else {
            
            Exc("Arquivo Vazio!");
            
        }
        
    }
    
    private void Action(String diretory){
        
        String folder = diretory.substring(0,diretory.lastIndexOf("\\"));
        folder += "\\";
        
        String file = diretory.substring(diretory.lastIndexOf("\\")+1);
        
        if(file.contains(".")){
            
            String ext = file.substring(file.indexOf("."));
            
            switch(ext.toLowerCase().replace(".", "")){
                
                case "csv" ->{
                    
                    Exportar(diretory, folder);
                    
                }
                
                default ->{
                    
                    Exc("O arquivo: \n" + file + "\nNao é CSV!");
                    
                }
                
            }
            
        } else {
            
            Exc("Arquivo \"" + diretory + "\" Inválido!");
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arq = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        arq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arqActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(arq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(arq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arqActionPerformed
        
        try{
            
            Action(arq.getSelectedFile().toString());
            
        }catch(Exception ev){
            
            Exc(ev.getMessage());
            
        }
        
    }//GEN-LAST:event_arqActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Open.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Open.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Open.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Open.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Open().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser arq;
    // End of variables declaration//GEN-END:variables
}
