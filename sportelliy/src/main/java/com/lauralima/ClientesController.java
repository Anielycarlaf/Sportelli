package com.lauralima;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class ClientesController {

    @FXML private TableColumn<?, ?> NomeCliente;
    @FXML private Button OnBtnHistoricoDeReservas;
    @FXML private Button OnBtnNovoCliente;
    @FXML private Button OnBtnPesquisar;
    @FXML private Button OnBtnRemoverClientes;
    @FXML private Button OnbtnAtualizar;
    @FXML private Button VoltarPAraMenu;
    @FXML private TableColumn<?, ?> cpfdoCliente;
    @FXML private TableView<?> tabeladeClientes;
    @FXML private TextField txtCpf;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNomedoCliente;
    @FXML private TextField txtTelefone;

    
     // Exibe o histórico de locações (Reservas de Locais e Equipamentos) do cliente selecionado.
     
    @FXML
    void HistoricodeReservasEqui(ActionEvent event) {
        Object clienteSelecionado = tabeladeClientes.getSelectionModel().getSelectedItem();
        
        if (clienteSelecionado == null) {
            new Alert(AlertType.WARNING, "Selecione um cliente na tabela para visualizar o histórico de reservas.").showAndWait();
            return;
        }

        // 1. TODO: Chamar o método Service/DAO para buscar o histórico de locações/reservas por Cliente
        // 2. Simulação de dados do histórico
        
        String nomeCliente = "Cliente Selecionado (Exemplo)"; 
        
        String historicoSimulado = "Histórico de Reservas para " + nomeCliente + ":\n\n" +
                                   "Data | Local/Equipamento | Status\n" +
                                   "---------------------------------------\n" +
                                   "28/11/2025 | Quadra de Futebol | Concluído - Pago\n" +
                                   "20/11/2025 | Kit Vôlei | Concluído - Devolvido\n" +
                                   "01/12/2025 | Ginásio Principal | Agendado - Pendente\n";
        
        // 3. Exibe o histórico em um pop-up
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Histórico de Locações");
        alert.setHeaderText("Reservas de Locais e Equipamentos");
        alert.setContentText(historicoSimulado);
        alert.showAndWait();
    }

    //Remove o cliente selecionado da tabela e do sistema.
    @FXML
    void RemoverClientes(ActionEvent event) {
        Object clienteSelecionado = tabeladeClientes.getSelectionModel().getSelectedItem();
        
        if (clienteSelecionado == null) {
            new Alert(AlertType.WARNING, "Selecione o cliente que deseja remover antes de prosseguir.").showAndWait();
            return;
        }

        // 1. Confirmação do Usuário
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Remoção");
        confirmacao.setHeaderText("Tem certeza que deseja remover este cliente?");
        confirmacao.setContentText("A remoção é permanente e pode falhar se houverem pendências financeiras ou locações ativas.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                // 2. TODO: Chamar o método Service/DAO para remover o cliente do banco
                // 3. TODO: Remover da lista da TableView e limpar os campos de texto
                // tabeladeClientes.getItems().remove(clienteSelecionado);

                new Alert(AlertType.INFORMATION, "O cliente foi excluído do sistema com sucesso.").showAndWait();
                             
            } catch (Exception e) {
                new Alert(AlertType.ERROR, "Não foi possível remover o cliente. Motivo: O cliente possui reservas ativas ou pendências financeiras.").showAndWait();
            }
        }
    }

        // Cadastra um novo cliente no sistema usando os dados dos campos de texto.
    @FXML
    void CadastrarNovoCliente(ActionEvent event) {
        // 1. Coleta e validação básica
        String nome = txtNomedoCliente.getText().trim();
        String email = txtEmail.getText().trim(); 
        
        if (nome.isEmpty() || email.isEmpty()) {
            new Alert(AlertType.WARNING, "Nome e Email são campos obrigatórios e não podem estar vazios.").showAndWait();
            return;
        }

        // 3. TODO: Chamar o método Service/DAO para salvar o novo cliente no banco de dados
        // clienteService.salvar(novoCliente); 
        
        // 5. Feedback visual
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cadastro Concluído");
        alert.setHeaderText("Cliente Registrado");
        alert.setContentText("O cliente " + nome + " foi adicionado ao sistema de locação.");
        alert.showAndWait();
    }

    // Pesquisa clientes pelo nome ou email e atualiza a tabela.
    @FXML
    void PesquisarClientes(ActionEvent event) {
        String termoPesquisa = txtNomedoCliente.getText().trim();
        
        if (termoPesquisa.isEmpty()) {
            new Alert(AlertType.INFORMATION, "Campo de pesquisa vazio. Exibindo todos os clientes.").showAndWait();
            // TODO: Chamar o método Service/DAO para carregar a lista completa novamente
            return;
        }
        
        // 1. TODO: Chamar o método Service/DAO para buscar no banco 
        // 2. Simulação de resultado (Substituir pela lista real)
        int resultadosEncontrados = 1; 

        // 3. TODO: Atualizar a tabela: tabeladeClientes.setItems(resultados);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pesquisa Concluída");
        alert.setHeaderText("Resultados Encontrados");
        alert.setContentText("Foram encontrados " + resultadosEncontrados + " clientes correspondentes ao termo '" + termoPesquisa + "'.");
        alert.showAndWait();
    }

    // Retorna para a tela do Menu Principal.
    @FXML
    void VoltarParaMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lauralima/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) VoltarPAraMenu.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Feedback de erro caso o arquivo FXML não seja encontrado
            new Alert(AlertType.ERROR, "Não foi possível carregar a tela do Menu. Verifique o caminho: /com/lauralima/Menu.fxml").showAndWait();
        }
    }
}
