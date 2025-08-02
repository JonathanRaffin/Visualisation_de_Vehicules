package animationvehicules;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

public class ChoixVisualisation {

	protected JFileChooser fileChooser;
	protected File file;

	/**
	 * Interface pour choisir le fichier à visualiser.
	 * 
	 * @throws InterruptedException
	 */
	public ChoixVisualisation(Controller ctrl) throws InterruptedException {
		this.initFrench();
		this.fileChooser = new JFileChooser(".");
		this.fileChooser.setDialogTitle("Choisissez une Instance ou une Solution");

		if (this.fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.file = this.fileChooser.getSelectedFile();
			String fileName = this.file.getName();
			fileName = fileName.substring(0, fileName.length() - 4);
			if (fileName.substring(0, 2).equals("is")) {
				fileName = "i" + fileName.substring(2, fileName.length());
				ctrl.lancerAnimation("is", fileName);
			} else  if (fileName.charAt(0) == 'i') {
				ctrl.lancerAnimation("i", fileName);
			} else if (fileName.charAt(0) == 's') {
				fileName = "i" + fileName.substring(1);
				ctrl.lancerAnimation("s", fileName);
			} else {
				System.out.println("Erreur sur le choix du fichier.");
			}
		}
	}

	/**
	 * Initialise les textes de l'interface en français.
	 */
	public void initFrench() {
		UIManager.put("FileChooser.openButtonText", "Ouvrir");
		UIManager.put("FileChooser.cancelButtonText", "Annuler");
		UIManager.put("FileChooser.lookInLabelText", "Rechercher:");
		UIManager.put("FileChooser.fileNameLabelText", "Nom du fichier:");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Type:");
		UIManager.put("FileChooser.acceptAllFileFilterText", "Tous les fichiers");
		UIManager.put("FileChooser.upFolderToolTipText", "Dossier parent");
		UIManager.put("FileChooser.homeFolderToolTipText", "Bureau");
		UIManager.put("FileChooser.newFolderToolTipText", "Créer un nouveau dossier");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Liste");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Détails");
	}
}