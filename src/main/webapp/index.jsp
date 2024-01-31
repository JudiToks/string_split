<%@ page import="java.util.List" %>
<%@ page import="mg.string_split.modeles.Produit" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    List<Produit> listProduit = (List<Produit>) request.getAttribute("listProduit");
%>

<%@include file="layout/header.jsp"%>

<main class="container">
    <h3>Liste de tous les produits : </h3><hr>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Marque</th>
                <th>Modele</th>
                <th>Prix</th>
                <th>Qualite</th>
            </tr>
        </thead>
        <tbody>
        <% if (listProduit != null) { %>
        <% for (int i = 0; i < listProduit.size(); i++) { %>
            <tr>
                <td><%=listProduit.get(i).getMarque()%></td>
                <td><%=listProduit.get(i).getNom()%></td>
                <td><%=listProduit.get(i).getPrix()%> Ariary</td>
                <td><%=listProduit.get(i).getQualite()%></td>
            </tr>
        <% } } %>
        </tbody>
    </table>
</main>

<%@include file="layout/footer.jsp"%>