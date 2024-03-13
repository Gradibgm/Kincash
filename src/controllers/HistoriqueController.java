/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.VenteController.previewRepport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import modele.Article;
import modele.Detailvente;
import modele.historiqueStock;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 * FXML Controller class
 *
 * @author omombo
 */
public class HistoriqueController implements Initializable {

    @FXML
    private TableView<Article> tabArticle;

    @FXML
    private TableColumn<Article, String> colNom;

    @FXML
    private TableColumn<Article, String> colPrix;

    @FXML
    private TableColumn<Article, String> colQuantite;

    @FXML
    private TableColumn<Article, String> colTotal;

    @FXML
    private TextField txtRecherche;

    @FXML
    private Label labNombre;

    @FXML
    private DatePicker dpDateRecherche;

    @FXML
    private TableView<historiqueStock> tabHistorique;

    @FXML
    private TableColumn<historiqueStock, String> colArticle;

    @FXML
    private TableColumn<historiqueStock, String> colType;

    @FXML
    private TableColumn<historiqueStock, String> colQuantiteModifier;

    @FXML
    private TableColumn<historiqueStock, String> colDate;

    @FXML
    void imprimer(ActionEvent event) {

    }

    @FXML
    void imprimerHistorique(ActionEvent event) {
        //imprimer();
    }

    @FXML
    void recherche(ActionEvent event) {

    }

    @FXML
    void rechercher(ActionEvent event) {

    }

    /* private void imprimer() {
        try {
            //1. Chargement du fichier Jrxml
            InputStream source = VenteController.class.getResourceAsStream("/Rapport/facture_kincash.jrxml");

            //2. Création de l'objet j'aspeReport
            JasperReport jasperReport = JasperCompileManager.compileReport(source);

            //3. Création de notre list HashMap (clé => valeur)
            ArrayList<HashMap<String, String>> listHashMap = new ArrayList<>();

            //4. Nous récupérons tous les objets depuis le tableVieux de commande
            ObservableList<Detailvente> listCommande = tabDetailVente.getItems();

            //5. Nous remplacons les valeur de chaque champs du rapport depuis notre commnade
            int numero = 1;
            for (Detailvente detailvente : listCommande) {
                //Création de liste des Champs de notre rapport
                HashMap<String, String> hashMap = new HashMap<>();
                double total = detailvente.getPrixvendu() * detailvente.getQuantiteVendu();

                hashMap.put("Num", String.valueOf(numero));
                hashMap.put("Article", detailvente.getArticle().getNom());
                hashMap.put("Prix_Unitaire", String.valueOf(detailvente.getArticle().getPrix()));
                hashMap.put("Quantites", String.valueOf(detailvente.getQuantiteVendu()));
                hashMap.put("Total", String.valueOf(total));
                numero = numero + 1;
                listHashMap.add(hashMap);
            }

            //6. Implementation du fichier PDF à imprimer
            JRBeanCollectionDataSource sourceInfo = new JRBeanCollectionDataSource(listHashMap);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), sourceInfo);

            //7. Exportation et affiche du PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "facture.pdf");

            File file = new File("facture.pdf");

            previewRepport(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Fin script*/
 /* public static void previewRepport(File file) {

        try {
            SwingNode swingNode = new SwingNode();
            StackPane stackPane = new StackPane();
            JScrollPane scrollPane = new JScrollPane();
            SwingController controller = new SwingController();
            SwingViewBuilder viewBuilder = new SwingViewBuilder(controller);

            JPanel panel = viewBuilder.buildViewerPanel();
            ComponentKeyBinding.install(controller, scrollPane);
            scrollPane.setViewportView(panel);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    swingNode.setContent(panel);
                }
            });

            stackPane.getChildren().add(swingNode);
            Stage stage = new Stage();
            stage.setScene(new Scene(stackPane, 250, 150));
            stage.setMaximized(true);
            stage.setTitle("Report viewr");
            stage.show();
            controller.openDocument(new FileInputStream(file), null, null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
         }
        //fin script*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Article> ListArticle = Article.recuperationArticle();
        tabArticle.setItems(ListArticle);
        colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colTotal.setCellValueFactory(
                (cell) -> {
                    //Récupérer l'article de la cellule
                    Article article = cell.getValue();
                    double total = article.getPrix() * article.getQuantite();
                    String totalConverti = String.valueOf(total);

                    return new SimpleStringProperty(totalConverti);
                }
        );

        ObservableList<historiqueStock> listHistorique = historiqueStock.getHistorique();
        tabHistorique.setItems(listHistorique);
        colArticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQuantiteModifier.setCellValueFactory(new PropertyValueFactory<>("quantiteModifie"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

}
