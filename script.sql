create table categorie(
    id_categorie serial primary key,
    nom varchar
);
create table marque(
    id_marque serial primary key,
    nom varchar,
    id_categorie int references categorie(id_categorie)
);
create table produit(
    id_produit serial primary key,
    nom varchar,
    id_marque int references marque(id_marque),
    id_categorie int references categorie(id_categorie),
    prix double precision,
    qualite int
);
create table filtre(
    id_filte serial primary key,
    nom varchar
);
create table filtre_methode(
    id_filtre_methode serial primary key,
    description varchar,
    ordre varchar,
    id_filtre int references filtre(id_filte)
);
-- meilleur prix, meilleur qualite/prix,

-- ###############################################################

select
    produit.nom,
    m.nom,
    c.nom,
    prix,
    qualite
from produit
    join marque m on produit.id_marque = m.id_marque
    join categorie c on m.id_categorie = c.id_categorie;

select
    ordre
from
    filtre_methode
        join filtre on filtre.id_filte = filtre_methode.id_filtre
where nom = 'meilleur' and param = 'prix';

select
    produit.nom,
    m.nom,
    c.nom,
    prix,
    qualite,
    ROUND(qualite/prix, 2) as rapport
from produit
         join marque m on produit.id_marque = m.id_marque
         join categorie c on m.id_categorie = c.id_categorie
order by prix asc;