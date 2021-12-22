package co.edu.unicauca.productconsumer.presentation;

import co.edu.unicauca.productconsumer.domain.Product;
import co.edu.unicauca.productconsumer.infra.Observer;
import co.edu.unicauca.productconsumer.domain.service.ServiceModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Es un observador
 *
 * @author ahurtado
 */
public class GUIListProductViewController extends javax.swing.JFrame implements Observer {
    
    ServiceModel service;
    JTable table;
    DefaultTableModel model;
    
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;

    /**
     * Creates new form GUIListadoProductos
     *
     * @param service servicio
     */
    public GUIListProductViewController(ServiceModel service) {
        initComponents();
        this.service = service;
        this.model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        table = new JTable(model);
        jPanel2.add(new JScrollPane(table));
        setSize(500, 200);
        setLocation(10,50);
    }


    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Listado de Productos");
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar);
        
        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_END);
        
        pack();
    }
    
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    @Override
    public void update(Object o) {
        model.setRowCount(0);
        for (Product p : service.listProducts()) {
            model.addRow(new Object[]{"" + p.getProductId(), p.getName(), p.getPrice()});
        }
        table.paintImmediately(table.getBounds());
    }
}
