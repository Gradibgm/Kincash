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
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

public class FenetrePrincipaleController implements Initializable {

    Article artilce;
    @FXML
    private Button btnArticle;

    @FXML
    private TableView<Article> tabArticle;

    @FXML
    private TableColumn<Article, String> colArticle;

    @FXML
    private TextField txtRechercheArticle;

    @FXML
    public void lancerCategorie(ActionEvent event) {
        Stage stageCategorie = lancerFenetre("/ui/Categorie.fxml", "Nouveau Categorie");
        stageCategorie.setResizable(false);
        stageCategorie.show();

    }

    @FXML
    public void lancerArticle(ActionEvent event) {

        Stage stageArticle = lancerFenetre("/ui/article.fxml", "Nouveau Article");
        stageArticle.setResizable(false);
        stageArticle.show();
        //   btnArticle.setDisable(true);

        /*  if (!btnArticle) {
            btnArticle.setDisable(true);
            
        } else {
            btnArticle.setDisable(false);
        }
         */
    }

    @FXML
    public void lancerClient(ActionEvent event) {
        Stage stageClient = lancerFenetre("/ui/Gradi", "Client");
        stageClient.setResizable(false);
        stageClient.show();
    }

    @FXML
    void lancerHistorique(ActionEvent event) {
        Stage stageHistorique = lancerFenetre("/ui/Historique.fxml", "Historique");
        stageHistorique.setResizable(false);
        stageHistorique.show();

    }

    @FXML
    public void lancerTaux(ActionEvent event) {

        Stage stageTaux = lancerFenetre("/ui/Taux.fxml", "Taux du jour");
        stageTaux.setResizable(false);
        stageTaux.show();

    }

    @FXML
    public void lancerUtilise(ActionEvent event) {
        Stage stageUtilisateur = lancerFenetre("/ui/Utilisateur.fxml", "Identité de L'utilisateur");
        stageUtilisateur.setResizable(false);
        stageUtilisateur.show();

    }

    @FXML
    public void lancerVente(ActionEvent event) {
        Stage stageVente = lancerFenetre("/ui/Vente.fxml", "Vente");
        stageVente.setResizable(false);
        stageVente.show();
    }

    @FXML
    public void client(ActionEvent event) {
        Stage stageClient = lancerFenetre("/ui/Client.fxml", "Ajouter Client");
        stageClient.setResizable(false);
        stageClient.show();

    }

    public Stage lancerFenetre(String cheminFenetre, String titreFenetre) {
        try {
            Parent fenetre = FXMLLoader.load(getClass().getResource(cheminFenetre));
            Scene scene = new Scene(fenetre);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titreFenetre);
            stage.show();
            return stage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @FXML
    private void supprimerArticle() {
    }

    @FXML
    private void modifierArticle() {

    }

    @FXML
    void ImprimerArticle(ActionEvent event) {
        imprimer();
    }

    @FXML
    private void approvisionnerArticle() {
        Article articleSelected = tabArticle.getSelectionModel().getSelectedItem();
        if (articleSelected != null) {
            try {
                //Chargement du fichier fxml
                FXMLLoader appro = new FXMLLoader(getClass().getResource("/ui/Appro.fxml"));
                Parent roo = appro.load();

                //Nous récupérons le controlleur Appro afin d'envoyer l'objet article
                ApproController approcontroller = appro.getController();

                //on passe l'objet articleSelected au controlleur de la fentre d'appro
                approcontroller.setArticle(articleSelected);

                Scene scene = new Scene(roo);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Approvisionnememnt");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Article> listArticle = Article.recuperationArticle();
        tabArticle.setItems(listArticle);
        colArticle.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        //Recherche
        FilteredList<Article> listArticleFiltre = new FilteredList<>(listArticle, list -> true);

        txtRechercheArticle.textProperty().addListener((Observable, oldValue, newValue) -> {
            listArticleFiltre.setPredicate((article) -> {

                if (newValue.isEmpty()) {
                    return true;
                }

                String rechercheEnMiniscule = newValue.toLowerCase();

                String nomArticleMiniscule = article.getNom().toLowerCase();
                String codeArticleMiniscule = article.getCode().toLowerCase();

                if (nomArticleMiniscule.contains(rechercheEnMiniscule)) {
                    return true;
                } else if (codeArticleMiniscule.contains(rechercheEnMiniscule)) {
                    return true;
                }

                return false;

            });

        });

        SortedList<Article> ListeArticleTriee = new SortedList<>(listArticleFiltre);

        ListeArticleTriee.comparatorProperty().bind(tabArticle.comparatorProperty());

        tabArticle.setItems(ListeArticleTriee);
        //Fin script
    }

    private void imprimer() {
        try {
            //1. Chargement du fichier Jrxml
            InputStream source = VenteController.class.getResourceAsStream("/Rapport/List_Article_kinCash.jrxml");

            //2. Création de l'objet j'aspeReport
            JasperReport jasperReport = JasperCompileManager.compileReport(source);

            //3. Création de notre list HashMap (clé => valeur)
            ArrayList<HashMap<String, String>> listHashMap = new ArrayList<>();

            //4. Nous récupérons tous les objets depuis le tableVieux de commande
            ObservableList<Article> listArticle = tabArticle.getItems();

            //5. Nous remplacons les valeur de chaque champs du rapport depuis notre commnade
            int numero = 1;
            for (Article article : listArticle) {
                //Création de liste des Champs de notre rapport
                HashMap<String, String> hashMap = new HashMap<>();
                //double total = detailvente.getPrixvendu() * detailvente.getQuantiteVendu();

                hashMap.put("Num", String.valueOf(numero));
                hashMap.put("Code", article.getCode());
                hashMap.put("Nom", article.getNom());
                hashMap.put("Prix", String.valueOf(article.getPrix()));

                numero = numero + 1;
                listHashMap.add(hashMap);
            }

            //6. Implementation du fichier PDF à imprimer
            JRBeanCollectionDataSource sourceInfo = new JRBeanCollectionDataSource(listHashMap);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), sourceInfo);

            //7. Exportation et affiche du PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "list des articles.pdf");

            File file = new File("list des articles.pdf");

            previewRepport(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Fin script

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

    public void refresh() {
        ObservableList<Article> listArticles = Article.recuperationArticle();
        tabArticle.setItems(listArticles);
        tabArticle.refresh();
    }

}
