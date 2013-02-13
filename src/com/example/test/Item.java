package com.example.test;

//import com.google.android.maps.GeoPoint;

public class Item {
	@Override
	public String toString() {
		return "Item [id=" + id + ", nom=" + nom + ", secteur=" + secteur
				+ ", quartier=" + quartier + ", categorie=" + categorie
				+ ", description=" + description + ", icone=" + icone
				+ ", image=" + image + ", lon=" + lon + ", lat=" + lat
				+ ", favori=" + favori + "]";
	}

	private long id;
	private String nom;
	private String secteur;
	private String quartier;
	private String categorie;
	private String description;
	private String icone;
	private String image;
	private double lon;
	private double lat;
	private boolean favori;

	public void Item() {
		favori = false;
		id = -1;
	}

	public long getId() {
		return id;
	}

	public Item(long id, String nom, String secteur, String quartier,
			String categorie, String description, String icone, String image,
			double lon, double lat, boolean favori) {
		super();
		this.id = id;
		this.nom = nom;
		this.secteur = secteur;
		this.quartier = quartier;
		this.categorie = categorie;
		this.description = description;
		this.icone = icone;
		this.image = image;
		this.lon = lon;
		this.lat = lat;
		this.favori = favori;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public boolean isFavori() {
		return favori;
	}

	public void setFavori(boolean favori) {
		this.favori = favori;
	}
	
//	public GeoPoint getGeoPoint(){
//		GeoPoint geopoint = new GeoPoint((int)(lat*1E6),(int)(lon*1E6));
//		return geopoint;
//	}

}
