PGDMP         "    
    
        {            projet8    15.3    15.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16428    projet8    DATABASE     ~   CREATE DATABASE projet8 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_Madagascar.1252';
    DROP DATABASE projet8;
                postgres    false            �            1259    16439    affectation    TABLE       CREATE TABLE public.affectation (
    numaffect character varying(5) NOT NULL,
    numemp character varying(5) NOT NULL,
    ancienlieu character varying(20) NOT NULL,
    nouveaulieu character varying(20) NOT NULL,
    dateaffec date NOT NULL,
    datepriseservice date NOT NULL
);
    DROP TABLE public.affectation;
       public         heap    postgres    false            �            1259    16429    employe    TABLE     4  CREATE TABLE public.employe (
    numemp character varying(5) NOT NULL,
    civilite character varying(4) NOT NULL,
    nom character varying(50) NOT NULL,
    prenoms character varying(50),
    mail character varying(50),
    poste character varying(25) NOT NULL,
    lieu character varying(20) NOT NULL
);
    DROP TABLE public.employe;
       public         heap    postgres    false            �            1259    16434    lieu    TABLE     �   CREATE TABLE public.lieu (
    idlieu character varying(5) NOT NULL,
    design character varying(20) NOT NULL,
    province character varying(20) NOT NULL
);
    DROP TABLE public.lieu;
       public         heap    postgres    false                      0    16439    affectation 
   TABLE DATA           n   COPY public.affectation (numaffect, numemp, ancienlieu, nouveaulieu, dateaffec, datepriseservice) FROM stdin;
    public          postgres    false    216   M                  0    16429    employe 
   TABLE DATA           T   COPY public.employe (numemp, civilite, nom, prenoms, mail, poste, lieu) FROM stdin;
    public          postgres    false    214   j                 0    16434    lieu 
   TABLE DATA           8   COPY public.lieu (idlieu, design, province) FROM stdin;
    public          postgres    false    215   �       q           2606    16443    affectation AFFECTATION_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.affectation
    ADD CONSTRAINT "AFFECTATION_pkey" PRIMARY KEY (numaffect);
 H   ALTER TABLE ONLY public.affectation DROP CONSTRAINT "AFFECTATION_pkey";
       public            postgres    false    216            m           2606    16445    employe EMPLOYE_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.employe
    ADD CONSTRAINT "EMPLOYE_pkey" PRIMARY KEY (numemp);
 @   ALTER TABLE ONLY public.employe DROP CONSTRAINT "EMPLOYE_pkey";
       public            postgres    false    214            o           2606    16438    lieu LIEU_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.lieu
    ADD CONSTRAINT "LIEU_pkey" PRIMARY KEY (idlieu);
 :   ALTER TABLE ONLY public.lieu DROP CONSTRAINT "LIEU_pkey";
       public            postgres    false    215                  x������ � �          u   x�300��-�rts���tͩ,>��35�(�!=713G/9?�3 #�$?�(� #�3$1�2��9ssS�z}=��9��+9�L������d&敀t�$�$�&�Tr��qqq ��'�         7   x��100�t�MJ�I,K��t�L�K,J�+)�O��100��gd�&Pec���� ��/     