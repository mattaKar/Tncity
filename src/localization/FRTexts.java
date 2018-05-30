package localization;

/**
 * French localized texts.
 */
public class FRTexts extends LocalizedTexts {

    // Messages
    @Override
    public String getToolCannotAffectMsg() {
        return "Ne peut pas avoir d'effet sur cette tuile";
    }

    @Override
    public String getCurrencyMsg() {
        return "£ {0}";
    }

    @Override
    public String getEarthQuakeMsg() {
        return "un tremblement de terre s'est produit [ {0} ]";
    }

    @Override
    public String getMissingResourcesMsg() {
        return "Ressources manquantes";
    }

    @Override
    public String getRoundMsg() {
        return "Round #{0} : {1}";
    }

    // Labels
    @Override
    public String getCurrencyLabel() {
        return "Argent";
    }

    @Override
    public String getUnconsumedEnergyLabel() {
        return "Energie non consomme";
    }

    @Override
    public String getStoredProductsLabel() {
        return "Produits stocké";
    }

    @Override
    public String getUnworkingPopulationLabel() {
        return "Chomeurs";
    }

    @Override
    public String getPopulationLabel() {
        return "Population";
    }
    
    @Override
    public String getWorkingPopulationLabel() {
        return "Population active";
    }

	@Override
	public String getUnconsumedWaterLabel() {
		return "Eau non consommé";
	}

	@Override
	public String getCareCapacityLabel() {
		return"Capacité hospitalière";
	}

	@Override
	public String getCellCapacityLabel() {
		return"Capacité des cellules au commissariat";
	}
	
	@Override
	public String getHappinessLabel() {
		return "bonheur";
	}

}
