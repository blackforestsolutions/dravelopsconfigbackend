package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Layers;

public class LayersObjectMother {

    private static final boolean DEFAULT_HAS_VENUE = true;
    private static final boolean DEFAULT_HAS_ADDRESS = true;
    private static final boolean DEFAULT_HAS_STREET = true;
    private static final boolean DEFAULT_HAS_LOCALITY = true;

    public static Layers getAddressAutocompletionQueryLayers() {
        Layers layers = new Layers();

        layers.setHasVenue(DEFAULT_HAS_VENUE);
        layers.setHasAddress(DEFAULT_HAS_ADDRESS);
        layers.setHasStreet(DEFAULT_HAS_STREET);
        layers.setHasLocality(DEFAULT_HAS_LOCALITY);
        return layers;
    }

    public static Layers getNearestAddressesQueryLayers() {
        Layers layers = new Layers();

        layers.setHasVenue(DEFAULT_HAS_VENUE);
        layers.setHasAddress(DEFAULT_HAS_ADDRESS);
        layers.setHasStreet(DEFAULT_HAS_STREET);
        layers.setHasLocality(DEFAULT_HAS_LOCALITY);
        return layers;
    }

}