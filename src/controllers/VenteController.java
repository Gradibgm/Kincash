/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import modele.Taux;
import modele.Utilisateur;
import modele.Vente;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

public class VenteController implements Initializable {

    @FXML
    private TextField txtRecherche;

    @FXML
    private TableView<Article> tabArticle;

    @FXML
    private TableView<Detailvente> tabDetailVente;

    @FXML
    private TableColumn<Article, String> colNomArticle;

    @FXML
    private TableColumn<Article, String> colPrixArticle;

    @FXML
    private TableColumn<Article, String> colQuantiteStock;

    @FXML
    private TableColumn<Detailvente, String> colDetailArticle;

    @FXML
    private TableColumn<Detailvente, String> colDetailPrixUnitaire;

    @FXML
    private TableColumn<Detailvente, String> colDetailQuantite;

    @FXML
    private TableColumn<Detailvente, String> colDetailPrixTotal;

    @FXML
    private Label labTaux;

    @FXML
    private Label labMontantTotalFC;

    @FXML
    private Label labMontantTotalUSD;

    @FXML
    private Label labMontantTotalTVA;
    //Configuration de la tableView détailVente
    private ObservableList<Detailvente> listCommande = FXCollections.observableArrayList();

    @FXML
    void ajouterPanier(ActionEvent event) {
        Article articleSelected = tabArticle.getSelectionModel().getSelectedItem();

        if (articleSelected != null) {
            //Nous cherchons si l'article existe dans la commande du client 
            Detailvente commandeExistante = null;

            //Nous parcourons la liste des commandes pour chaque article s'y trouvant.
            for (Detailvente commande : listCommande) {
                //Nous récupérons chaque article de chaque commande et virifions si l'article
                //Selectionné estv égal a l'un des articles de notre commande
                if (commande.getArticle() == articleSelected) {
                    //Nous actualisons notre commande
                    commandeExistante = commande;
                    //Nous arretons de comaprer chaque article tout en sortant de notre boucle
                    break;
                }
            }

            if (commandeExistante != null) {
                int quantiteCommandee = commandeExistante.getQuantiteVendu() + 1;
                int stockActuel = commandeExistante.getArticle().getQuantite();
                double prixUnitaire = commandeExistante.getArticle().getPrix();

                if (quantiteCommandee > stockActuel) {
                    ArticleController.showArlertError("Quantité limitée à " + stockActuel, "Stock depassé");
                    return;
                }

                commandeExistante.setQuantiteVendu(quantiteCommandee);
                commandeExistante.setPrixvendu(articleSelected.getPrix());

            } else {

                Vente vente = new Vente();
                double PrixVendu = articleSelected.getPrix();
                Detailvente nouvelleCommande = new Detailvente(1, PrixVendu, articleSelected, vente);

                //Nous rajoutons l'article selectionner dans le tableView
                listCommande.add(nouvelleCommande);
                tabDetailVente.setItems(listCommande);

            }

            double montantTotal = calculMontantTotal();
            labMontantTotalFC.setText(String.valueOf(montantTotal));

            double montantEnUSD = convertirMontantTotalEnUSD(montantTotal);
            labMontantTotalUSD.setText(String.valueOf(montantEnUSD));

            double montantSansTVA = calculMontantTotalSansTVA(montantTotal);
            labMontantTotalTVA.setText(String.valueOf(montantSansTVA));

            tabDetailVente.refresh();

        }

    }

    private double calculMontantTotal() {
        double montantTotal = 0;

        for (Detailvente detailvente : listCommande) {
            double prixTotal = detailvente.getPrixvendu() * detailvente.getQuantiteVendu();
            montantTotal = prixTotal + montantTotal;
        }
        return montantTotal;
    }

    private double convertirMontantTotalEnUSD(double montantTotal) {
        Taux tauxActuel = Taux.recuperationTauxActuel();
        double montantEnUSD = montantTotal / tauxActuel.getMontant();
        return montantEnUSD;
    }

    private double calculMontantTotalSansTVA(double montantTotal) {
        double tva = (montantTotal * 16) / 100;
        double montantSansTva = montantTotal - tva;

        return montantSansTva;

    }

    @FXML
    void reduireQuantitePanier(ActionEvent event) {

    }

    @FXML
    void retirerPanier(ActionEvent event) {

    }

    @FXML
    void terminerVente(ActionEvent event) {
        if (listCommande.isEmpty()) {
            ArticleController.showArlertError("Commande vide, Veillez Ajouter des élément", "Commande");
            return;
        }

        double prixTotal = calculMontantTotal();
        Taux taux = Taux.recuperationTauxActuel();
        Utilisateur utilisateur = new Utilisateur(1, "", "", "");

        Vente vente = new Vente(prixTotal, utilisateur, taux);

        boolean insererVente = vente.insertionvente(listCommande);
        if (insererVente) {
            //ArticleController.showArlertInformation("ok", "Inserstion");
            imprimer();
        } else {
            ArticleController.showArlertError("Erreur", "Insertion");
        }
    }
    //Méthode d'impression
    private void imprimer() {
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
                double total = detailvente.getPrixvendu()*detailvente.getQuantiteVendu();
                
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
        //Fin script
    }
    //Méthode pour le lancement de la fenetre d'impression
    public static void previewRepport(File file) {

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
        
        //fin script

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Récupération des articles afin de les placer dans la tableview articles 
        ObservableList<Article> listArticles = Article.recuperationArticle();
        //Nous placons la liste dans le tableview
        tabArticle.setItems(listArticles);
        //configuration de collone
        colNomArticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrixArticle.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantiteStock.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        //Récupérer le taux actuel
        Taux taux = Taux.recuperationTauxActuel();
        labTaux.setText("Taux du jour  : " + String.valueOf(taux.getMontant()));

        //Configuration des collones du tableView détailVente
        colDetailArticle.setCellValueFactory(
                (cell) -> {
                    Detailvente detailvente = cell.getValue();
                    Article article = detailvente.getArticle();
                    String nom = article.getNom();
                    return new SimpleStringProperty(nom);
                }
        );

        colDetailPrixUnitaire.setCellValueFactory(
                (cell) -> {
                    Detailvente detailvente = cell.getValue();
                    Article article = detailvente.getArticle();
                    double prixUnitaire = article.getPrix();
                    return new SimpleStringProperty(String.valueOf(prixUnitaire));
                }
        );

        colDetailQuantite.setCellValueFactory(new PropertyValueFactory<>("quantiteVendu"));

        colDetailPrixTotal.setCellValueFactory(
                (cell) -> {
                    Detailvente detailvente = cell.getValue();
                    Article article = detailvente.getArticle();

                    double prixTotal = article.getPrix() * detailvente.getQuantiteVendu();

                    return new SimpleStringProperty(String.valueOf(prixTotal));
                }
        );

    }

}
