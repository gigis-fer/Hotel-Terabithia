val nomeHotel:  String = "Hotel Calypse"

var totalReservasConfirmadas = 0
var quartosOcupados = 0
var quantidadeHospedes = 0
var quantidadeEventos = 0

var receitaHospedagem = 0.0
var receitaEventos = 0.0

data class Hospede(
    var nome: String,
    val registro: String
)
    var nomeUsuario:  String =""

fun main(){
    println("Bem-Vindo(a) ao $nomeHotel !")
    print("Digite o nome de usuário: ")
    val nomeUsuario = readLine()

    var tentativasRestantes = 3
    var verificador = false
    val senhaCorreta = "2678"

    while (tentativasRestantes > 0 && !verificador) {
        print("Digite a senha (obrigatória): ")
        val senhaDigitada = readln()

        if (senhaDigitada == senhaCorreta) {
            verificador = true
        } else {
            tentativasRestantes--
            if (tentativasRestantes > 0) {
                println("Senha incorreta! Você ainda tem $tentativasRestantes tentativa(s).")
            } else {
                println("Acesso negado! Sistema bloqueado devido ao excesso de tentativas.")
            }
        }
    }
    if (verificador) {
        println("Bem-vindo ao Hotel $nomeHotel, $nomeUsuario. É um imenso prazer ter você por aqui!")
    }
    inicio()
}

fun inicio() {
    println("Menu Principal")
    println("1. Reservas de Quartos")
    println("2. Cadastro de Hóspedes")
    println("3. Eventos")
    println("4. Ar-Condicionado")
    println("5. Abastecimento")
    println("6. Relatórios Operacionais")
    println("7. Sair")
    print("Escolha uma opção: ")
    val opcao = readln().toIntOrNull()

    when (opcao) {
        1->reservaQuartos()
            2->cadastrarHospedes()
                3->eventos()
                    4->arCondiconado()
        5->abastecimento()
        6->relatorioOperacional()
        7->sairHotel()

    }

}

// Reserva de quartos
fun reservaQuartos() {
    val quartosOcupados = BooleanArray(21) { false }
    var menuPrincipal = true

    while (menuPrincipal) {
        println("[Reservas]")
        print("Informe o valor da diária: ")
        val valorDiaria = readln().toDoubleOrNull() ?: 1.0

        print("Informe a quantidade de diárias (1-30): ")
        val qtdDiarias = readln().toIntOrNull() ?: 1

        // Validação das diárias de acordo com os requisitos
        if (valorDiaria <= 0 || qtdDiarias !in 1..30) {
            println("Valor Inválido")
            continue
        }

        print("Informe o nome do hóspede: ")
        val nomeHospede = readln().trim()

        print("Tipo de quarto (S/E/L): ")
        val tipoQuarto = readln().uppercase().trim()

        val (fator, nomeTipo) = when (tipoQuarto) {
            "S" -> 1.00 to "Standard"
            "E" -> 1.35 to "Executivo"
            "L" -> 1.65 to "Luxo"
            else -> 1.00 to "Standard"
        }

        var quartoEscolhido = 0
        var quartoValido = false

        while (!quartoValido) {
            print("Escolha um quarto (1-20): ")
            quartoEscolhido = readln().toIntOrNull() ?: 0

            if (quartoEscolhido !in 1..20) {
                println("Número do quarto inválido. Escolha de 1 a 20.")
            } else if (quartosOcupados[quartoEscolhido]) {
                println("Quarto já está ocupado")

                print("Quartos disponíveis: ")
                val livres = mutableListOf<Int>()
                for (i in 1..20) {
                    if (!quartosOcupados[i]) livres.add(i)
                }
                println(livres.joinToString(", "))
            } else {
                quartoValido = true
            }
        }

        val subtotal = valorDiaria * qtdDiarias * fator
        val taxaServico = subtotal * 0.10
        val totalFinal = subtotal + taxaServico

        println("\nResumo:")
        println("Hóspede: %s".format(nomeHospede))
        println("Quarto: %d (%s)".format(quartoEscolhido, nomeTipo))
        println("Subtotal: R$ %.2f".format(subtotal))
        println("Taxa de serviço (10%%): R$ %.2f".format(taxaServico))
        println("Total: R$ %.2f".format(totalFinal))


        println("\n%s, confirma a reserva? (S/N): ".format(nomeHospede))
        var confirma = readln().uppercase().trim()

        while (confirma != "S" && confirma != "N") {
            print("Comando inválido. Digite S ou N: ")
            confirma = readln().uppercase().trim()
        }

        if (confirma == "S") {
            quartosOcupados[quartoEscolhido] = true

            totalReservasConfirmadas++
            receitaHospedagem += totalFinal

            println("Reserva efetuada com sucesso!")
        } else {
            println("Reserva não efetuada.")
        }

        println("Mapa de Quartos (L - Livres e O - Ocupados")
        var quartoAtual = 1
        for (linha in 1..4) {
            val linhaQuartos = mutableListOf<String>()
            for (coluna in 1..5) {
                val status = if (quartosOcupados[quartoAtual]) "O" else "L"
                linhaQuartos.add("%02d:%s".format(quartoAtual, status))
                quartoAtual++
            }
            println(linhaQuartos.joinToString("   "))
        }
        println("-----------------------------")

        print("\nDeseja voltar ao menu principal de reservas? (S/N): ")
        var opcaoMenu = readln().uppercase().trim()
        while (opcaoMenu != "S" && opcaoMenu != "N") {
            print("Digite S ou N: ")
            opcaoMenu = readln().uppercase().trim()
        }
        if (opcaoMenu == "N") {
            menuPrincipal = false
        }
    }
    inicio()
}

// Cadastro
fun cadastrarHospedes() {
    val listaHospedes = mutableListOf<Hospede>()
    var contadorOrdem = 1

    while (true) {
        println("""
             [Cadastro de Hóspedes] 
            1. Cadastrar
            2. Pesquisar por nome exato
            3. Pesquisar por prefixo
            4. Listar hóspedes (A-Z)
            5. Atualizar cadastro
            6. Remover cadastro
            7. Voltar ao Menu Principal
            Escolha uma opção: """.trimIndent())

        val escolha = readln().toIntOrNull()

        when (escolha) {
            1 -> { // Cadastrar Hospede
                if (listaHospedes.size >= 15) {
                    println("Máximo de cadastros atingido")
                } else {
                    print("Nome do hóspede: ")
                    val nome = readln().trim()

                    if (nome.isBlank()) {
                        println("Valor inválido.")
                    } else if (listaHospedes.any { it.nome.equals(nome, ignoreCase = true) }) {
                        println("Hóspede já cadastrado")
                    } else {
                        val marcaReg = "Hóspede nº %d".format(contadorOrdem++)
                        listaHospedes.add(Hospede(nome, marcaReg))
                        quantidadeHospedes++
                        println("Hóspede cadastrado com sucesso.")
                    }
                }
            }
            2 -> { // Pesquisar pelo nome
                print("Informe o nome completo do hóspede: ")
                val nomeBusca = readln().trim()
                val encontrado = listaHospedes.find { it.nome.equals(nomeBusca, ignoreCase = true) }

                if (encontrado != null) {
                    println("Hóspede %s foi encontrado.".format(encontrado.nome))
                } else {
                    println("Hóspede não encontrado")
                }
            }
            3 -> { // Pesquisar pelo prefixo
                print("Informe o Prefixo do nome: ")
                val prefixo = readln().trim()

                if (prefixo.isBlank()) {
                    println("Valor inválido.")
                } else {
                    val resultados = listaHospedes.filter { it.nome.startsWith(prefixo, ignoreCase = true) }

                    if (resultados.isNotEmpty()) {
                        println("Resultados:")
                        resultados.forEachIndexed { index, h ->
                            println("[%d] %s".format(index + 1, h.nome))
                        }
                    } else {
                        println("Hóspede não encontrado.")
                    }
                }
            }
            4 -> {
                if (listaHospedes.isEmpty()) {
                    println("Não há hóspedes cadastrados.")
                } else {
                    println("\n--- [Lista de Hóspedes (A-Z)] ---")
                    val listaOrdenada = listaHospedes.sortedBy { it.nome }
                    listaOrdenada.forEachIndexed { index, h ->
                        println("[%d] %s (%s)".format(index + 1, h.nome, h.registro))
                    }
                }
            }
            5 -> {
                if (listaHospedes.isEmpty()) {
                    println("Não há hóspedes para atualizar.")
                } else {
                    println("\n--- [Atualizar Cadastro] ---")
                    val listaOrdenada = listaHospedes.sortedBy { it.nome }
                    listaOrdenada.forEachIndexed { index, h ->
                        println("[%d] %s".format(index + 1, h.nome))
                    }

                    print("Digite o número do hóspede que deseja atualizar: ")
                    val indice = readln().toIntOrNull()

                    if (indice != null && indice in 1..listaOrdenada.size) {
                        val hospedeOriginal = listaOrdenada[indice - 1]
                        print("Novo nome: ")
                        val novoNome = readln().trim()

                        if (novoNome.isNotBlank()) {
                            val indexReal = listaHospedes.indexOf(hospedeOriginal)
                            listaHospedes[indexReal].nome = novoNome
                            println("Operação realizada com sucesso")
                        } else {
                            println("Nome inválido.")
                        }
                    } else {
                        println("Índice inválido.")
                    }
                }
            }
            6 -> {
                if (listaHospedes.isEmpty()) {
                    println("Não há hóspedes para remover.")
                } else {
                    println("\n--- [Remover Cadastro] ---")
                    val listaOrdenada = listaHospedes.sortedBy { it.nome }
                    listaOrdenada.forEachIndexed { index, h ->
                        println("[%d] %s".format(index + 1, h.nome))
                    }

                    print("Digite o número do hóspede que deseja remover: ")
                    val indice = readln().toIntOrNull()

                    if (indice != null && indice in 1..listaOrdenada.size) {
                        val hospedeParaRemover = listaOrdenada[indice - 1]
                        listaHospedes.remove(hospedeParaRemover)
                        quantidadeHospedes--
                        println("Operação realizada com sucesso")
                    } else {
                        println("Índice inválido.")
                    }
                }
            }
            7 -> {
                println("Voltando ao menu principal...")
                return
            }
            else -> {
                println("Opção inválida!")
            }
        }
    }
    inicio()
}

//Eventos

fun eventos(): Double {
    // 6.1 Parte A- Capacidade e seleção de auditório
    println("Eventos")
    print("Informe o número de convidados: ")
    val convidados = readln().toIntOrNull() ?: 1

    if (convidados <= 0 || convidados > 350) {
        println("Número de convidados inválido.")
        return 0.0 }

    println("Número de convidados válido.")
    val auditorio: String
    var cadeiras_adicionais = 0

    if (convidados <= 220) {
        auditorio= "Laranja"
        if (convidados > 150) {
            cadeiras_adicionais = convidados - 150
            println("Auditório selecionado: $auditorio ($cadeiras_adicionais cadeiras adicionais)")
        } else {
            println("Auditório selecionado: $auditorio")
        }
    } else {
        auditorio = "Colorado"
        println("Auditório selecionado: $auditorio")
    }

    // 6.2 Parte B- Agenda e disponibilidade
    print("Informe o dia da semana (ex: segunda, terca, sexta): ")
    val dia = readln().lowercase().trim()

    val dias_Uteis = listOf("segunda", "terca", "terça", "quarta", "quinta", "sexta")
    val fimDeSemana = listOf("sabado", "sábado", "domingo")

    if (dia in dias_Uteis) {
        println("\n=======================================================")
        println(" TABELA DE HORÁRIOS DISPONÍVEIS - SEGUNDA A SEXTA (07h às 23h)")
        println("=======================================================")
        println("[07h] [08h] [09h] [10h] [11h] [12h] [13h] [14h]")
        println("[15h] [16h] [17h] [18h] [19h] [20h] [21h] [22h] [23h]")
        println("=======================================================")
    } else if (dia in fimDeSemana) {
        println("\n=======================================================")
        println(" TABELA DE HORÁRIOS DISPONÍVEIS - SÁBADO E DOMINGO (07h às 15h)")
        println("=======================================================")
        println("[07h] [08h] [09h] [10h] [11h] [12h] [13h] [14h] [15h]")
        println("=======================================================\n")
    } else {
        println("Dia da semana indisponível.")
        return 0.0
    }

    print("Informe o horário inicial do evento (07 a 23): ")
    val horaInicio = readln().toIntOrNull() ?: -1

    print("Informe a duração do evento em horas (1 a 12): ")
    val duracao = readln().toIntOrNull() ?: -1

    if (duracao !in 1..12) {
        println("Duração inválida. O Evento deve durar de 1 a 12 horas. ")
        return 0.0
    }

    val horaFim = horaInicio + duracao

    if (dia in dias_Uteis && (horaInicio < 7 || horaFim > 23)) {
        println("Auditório Indisponível. Para dias úteis, o evento deve iniciar a partir das 07h e encerrar até às 23h.")
        return 0.0
    } else if (dia in fimDeSemana && (horaInicio < 7 || horaFim > 15)) {
        println("Auditório indisponível. Para fins de semana, o evento deve iniciar a partir das 07h e encerrar até às 15h.")
        return 0.0
    }

    print("Qual o nome da empresa contratante: ")
    val nomeEmpresa = readln().trim()
    println("Auditório reservado.")

    // 6.3 Parte C- Equipe de garçons (Arredondamento para cimaa)
    val garconsBase = (convidados + 12 - 1) / 12
    val garconsReforco = duracao / 2
    val total_Garcons = garconsBase + garconsReforco
    val custoGarcons = total_Garcons * duracao * 10.50

    // 6.4 Parte D- Serviço de Buffet
    val quantidadeCafe = convidados * 0.2
    val custoCafe = quantidadeCafe * 0.80

    val quantidadeAgua = convidados * 0.5
    val custoAgua = quantidadeAgua * 0.40

    val quantidadeSalgados = convidados * 7
    val custoSalgados = quantidadeSalgados * 0.34

    val custoBuffet = custoCafe + custoAgua + custoSalgados
    val totalGeral = custoGarcons + custoBuffet

    //6.5 Parte E- Relatório Técnico
    println("\n----------------------------------------------------")
    println("  Relatório do Evento  ")
    println("Auditório: %s".format(if (cadeiras_adicionais > 0) "$auditorio ($cadeiras_adicionais cadeiras adicionais)" else auditorio))
    println("Empresa: %s".format(nomeEmpresa))
    println("Data/Hora: %s das %dhs às %dhs".format(dia, horaInicio, horaFim))
    println("Convidados: %d | Duração: %d horas".format(convidados, duracao))
    println("Garçons necessários: %d".format(total_Garcons))
    println("---------------------------------------------------")
    println("Custo com garçons: R$ %.2f".format(custoGarcons))
    println("Buffet - Café: %.1f L | Água: %.1f L | Salgados: %d un".format(quantidadeCafe, quantidadeAgua, quantidadeSalgados))
    println("Custo do buffet: R$ %.2f".format(custoBuffet))
    println("---------------------------------------------------")
    println("Total do Evento: R$ %.2f".format(totalGeral))
    println("----------------------------------------------------")

    // Decisão e confirmação de reserva
    print("Confirmar reserva? (S/N): ")
    var confirma = readln().uppercase().trim()

    while (confirma != "S" && confirma != "N") {
        print("Comando inválido. Digite S ou N: ")
        confirma = readln().uppercase().trim()
    }

    if (confirma == "S") {
        quantidadeEventos++
        receitaEventos += totalGeral
        println("Reserva efetuada com sucesso.")
        return totalGeral
    } else {
        println("Reserva não efetuada.")
        return 0.0
    }
    inicio()
}
// Ar-Condicionado
fun arCondiconado() {
    var empresaBarata = ""
    var menorValor = Double.MAX_VALUE
    var empresaCara = ""
    var maiorValor = 0.0

    var totalEmpresasCadastradas = 0
    var continuar = true

    println("Ar-Condicionado - Comparativo Técnico")
// 7.1 Parte A- Requisitod
    while (continuar) {
        println("Cadastro de Empresa")
        print("Nome da empresa: ")
        val nomeEmpresa = readln()

        print("Valor por aparelho (R\$): ")
        val valorPorAparelho = readln().toDoubleOrNull() ?: 0.0

        print("Quantidade de aparelhos: ")
        val qtdAparelho = readln().toIntOrNull() ?: 0

        print("Percentual de desconto (%): ")
        val porcentagemDesconto = readln().toDoubleOrNull() ?: 0.0

        print("Quantidade mínima para desconto: ")
        val qtdMinima = readln().toIntOrNull() ?: 0

        print("Valor fixo de deslocamento (R\$): ")
        val deslocamento = readln().toDoubleOrNull() ?: 0.0

        val valorTotal = calcularOrcamento(
            valorPorAparelho,
            qtdAparelho,
            porcentagemDesconto,
            qtdMinima,
            deslocamento
        )
        totalEmpresasCadastradas++

        if (totalEmpresasCadastradas == 1) {
            maiorValor = valorTotal
            empresaCara = nomeEmpresa
            menorValor = valorTotal
            empresaBarata = nomeEmpresa
        } else {
            if (valorTotal > maiorValor) {
                maiorValor = valorTotal
                empresaCara = nomeEmpresa
            }
            if (valorTotal < menorValor) {
                menorValor = valorTotal
                empresaBarata = nomeEmpresa
            }
        }

        println("O serviço de %s custará R$ %.2f".format(nomeEmpresa, valorTotal))

        print("\nDeseja informar novos dados? (S/N): ")
        var respostaContinuar = readln().uppercase().trim()

        while (respostaContinuar != "S" && respostaContinuar != "N") {
            print("Comando inválido. Digite S ou N: ")
            respostaContinuar = readln().uppercase().trim()
        }

        if (respostaContinuar == "N") {
            continuar = false
        }
    }

    if (totalEmpresasCadastradas >= 2) {
        val diferencaPercentual = ((maiorValor - menorValor) / menorValor) * 100

        println("\n-----------------Resumo Final -----------------")
        println("O orçamento de menor valor é o de %s por R$ %.2f".format(empresaBarata, menorValor))
        println("O orçamento de maior valor é o de %s por R$ %.2f".format(empresaCara, maiorValor))
        println("A diferença percentual entre melhor e pior proposta é de %.2f%%".format(diferencaPercentual))
        println("-----------------------------------------------")
    } else {
        println("\nÉ necessário informar ao menos duas empresas para exibir o comparativo final.")
        arCondiconado()

    }
    inicio()
}

fun calcularOrcamento(
    valorAparelho: Double,
    quantidade: Int,
    percentualDesconto: Double,
    qtdMinimo: Int,
    deslocamento: Double
): Double {
    val brutoTotal = valorAparelho * quantidade

    val desconto = if (quantidade >= qtdMinimo) {
        brutoTotal * (percentualDesconto / 100.0)
    } else {
        0.0
    }
    return brutoTotal - desconto + deslocamento
}

// Abastecimento
fun abastecimento() {
    println("\nAbastecimento")
    //Posto 1- Wayne
    println("--- Posto Wayne Oil ---")
    print("Preço do Álcool: ")
    val alcoolWayne = readln().toDoubleOrNull() ?: 0.0
    print("Preço da Gasolina: ")
    val gasolinaWayne = readln().toDoubleOrNull() ?: 0.0

    // Posto 2- Stark Petrol
    println("\n--- Posto Stark Petrol ---")
    print("Preço do Álcool: ")
    val alcoolStark = readln().toDoubleOrNull() ?: 0.0
    print("Preço da Gasolina: ")
    val gasolinaStark = readln().toDoubleOrNull() ?: 0.0

    val litrosTanque = 42

    // Álcool é vantajoso se for 30% mais barato (ou seja, até 70% do preço da gasolina)
    val combustivelWayne: String
    val precoWayne: Double
    if (alcoolWayne <= gasolinaWayne * 0.70) {
        combustivelWayne = "Álcool"
        precoWayne = alcoolWayne
    } else {
        combustivelWayne = "Gasolina"
        precoWayne = gasolinaWayne
    }
    val totalWayne = precoWayne * litrosTanque

    val combustivelStark: String
    val precoStark: Double
    if (alcoolStark <= gasolinaStark * 0.70) {
        combustivelStark = "Álcool"
        precoStark = alcoolStark
    } else {
        combustivelStark = "Gasolina"
        precoStark = gasolinaStark
    }
    val totalStark = precoStark * litrosTanque


    println("\nWayne Oil: melhor opção = %s | Total (42L) = R$ %.2f".format(combustivelWayne, totalWayne))
    println("Stark Petrol: melhor opção = %s | Total (42L) = R$ %.2f".format(combustivelStark, totalStark))

    val melhorPosto: String
    val melhorCombustivel: String

    if (totalWayne <= totalStark) {
        melhorPosto = "Wayne Oil"
        melhorCombustivel = combustivelWayne.lowercase()
    } else {
        melhorPosto = "Stark Petrol"
        melhorCombustivel = combustivelStark.lowercase()
    }

    println("\n%s, é mais barato abastecer com %s no posto %s.".format(nomeUsuario, melhorCombustivel, melhorPosto))
    inicio()
}

//Relatórios Operacionais
fun relatorioOperacional() {

    val taxaOcupacao = (quartosOcupados.toDouble() / 20.0) * 100
    val receitaTotalGeral = receitaHospedagem + receitaEventos

    println("--------RELATÓRIO OPERACIONAL DO HOTEL CALYPSE--------")
    println("Total de reservas de quartos confirmadas: $totalReservasConfirmadas")
    println(
        "Taxa de ocupação atual: %.2f%% (%d/20 quartos)"
            .format(taxaOcupacao, quartosOcupados)
    )

    println("Quantidade de hóspedes cadastrados: $quantidadeHospedes")
    println("Quantidade de eventos confirmados: $quantidadeEventos")
    println("----------------------------------------------")
    println("RECEITA ACUMULADA")
    println("----------------------------------------------")
    println("Hospedagem: R$ %.2f".format(receitaHospedagem))
    println("Eventos:    R$ %.2f".format(receitaEventos))
    println("----------------------------------------------")
    println("Total geral: R$ %.2f".format(receitaTotalGeral))
    println("\n")
    inicio()
}

fun sairHotel() {
    print("Você deseja sair? (S/N): ")
    val resposta = readln().trim().uppercase()

    if (resposta == "S" || resposta == "SIM") {
        println("Muito obrigado e até logo!")
        false
    } else {
        println("Voltando ao menu principal...")
    }
}