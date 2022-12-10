// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

enum class Nivel { BASICO, INTERMEDIARIO, AVANCADO }

data class Usuario(val nome: String, val cpfcnpj: Long, val dataNascimento: String) {
    var idade: Int = CalcularIdade()
    
    fun CalcularIdade(): Int {
    	val anoAtual: Int = LocalDate.now().year
        val nascimento: Int = LocalDate.parse(this.dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")).year
        
        if (anoAtual > nascimento) {
        	return anoAtual - nascimento    
        } else {
            return 0
        }
    }
}

data class ConteudoEducacional(val nome: String, val nivel: Nivel, val duracao: Int = 60)

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>, val IdadeMin: Int = 0) {

    val inscritos = mutableListOf<Usuario>()
    val nivel: Nivel = definirNivel()
    val duracao: Int = definirDuracao()
    
    fun  definirNivel():Nivel {
        var basico: Int = 0
        var intermediario: Int = 0
        var avancado: Int = 0
        
        for (conteudos in this.conteudos) {
           when (conteudos.nivel) {
           	   Nivel.BASICO -> basico += 1
               Nivel.INTERMEDIARIO -> intermediario += 1
               Nivel.AVANCADO -> avancado += 1
           }
        }
    	
        when (maxOf(basico, intermediario, avancado)) {
            basico -> return Nivel.BASICO
            intermediario -> return Nivel.INTERMEDIARIO
            avancado -> return Nivel.AVANCADO
            else -> println("Log: Erro na Formacao.definirNivel()")
        }     
        return Nivel.BASICO
    }    

    fun definirDuracao(): Int {
        var duracao: Int = 0
        
    	for (conteudos in this.conteudos) {
        	duracao += conteudos.duracao    
        }   
        return duracao
    }
    
    fun matricular(usuario: Usuario) {
        //TODO("Utilize o parâmetro $usuario para simular uma matrícula (usar a lista de $inscritos).")
        var jaMatriculado: Boolean = false
        
        for (inscrito in this.inscritos) {
        	if (inscrito.cpfcnpj == usuario.cpfcnpj) {
            	jaMatriculado = true
                break
            }                
        }
        
        if (jaMatriculado) {
            println("O usuário ${usuario.nome} já foi cadastrado no ${this.nome}.")
        } else if (usuario.idade < this.IdadeMin) {
            println("A idade minima para se matricular nessa formação é de ${this.IdadeMin} anos e o usuário ${usuario.nome} tem ${usuario.idade} anos.")
        } else {
            this.inscritos.add(usuario)
            println("O usuário ${usuario.nome} foi matriculado no ${this.nome} de nivel ${this.nivel} com carga horaria total de ${this.duracao}h.")
        }
    }
    
    fun DetalharFormacao() {
        fun desenharlinhas(){
           for (i in 1..50) {
               print("-=")    
           }  
           println()
        }
        
        
        desenharlinhas()
        println("A formação ${this.nome} de nível ${this.nivel} e carga horária de ${this.duracao}h tem os seguintes conteúdos: ")
        
        println()
        println("%-40s %-15s %-8s".format("Nome", "Nivel", "Duração"))
        for (conteudo in this.conteudos) {
            println("%-40s %-15s %-8s".format(conteudo.nome, conteudo.nivel, "%d".format(conteudo.duracao).plus("h")))
        }
        
        println()
        
        if (this.IdadeMin > 0) {
            println("A idade mínima para se inscrever é de ${this.IdadeMin} anos.")
        } else {
            println("Não existe idade mínima para se inscrever.")
        }
        
        println()
        println("Os inscritos são: ")
        println()
        
        for (inscrito in this.inscritos) {
            println("${inscrito.nome}, ${inscrito.idade} anos, nascido em ${inscrito.dataNascimento}.")
        }
        desenharlinhas()
    }
}

fun main() {
    //TODO("Analise as classes modeladas para este domínio de aplicação e pense em formas de evoluí-las.")
    //TODO("Simule alguns cenários de teste. Para isso, crie alguns objetos usando as classes em questão.")

    val usuario1 = Usuario("Lidson", 25274397655, "02/05/2002");
    val usuario2 = Usuario("Joao", 32334846372, "23/09/1986");
    val usuario3 = Usuario("Ana Paula", 52930234433, "31/12/2005")

    val cursoPython = ConteudoEducacional("Curso de Python", Nivel.AVANCADO)
    val cursoPHP = ConteudoEducacional("Curso de PHP", Nivel.INTERMEDIARIO, 40)
    val liveBackEnd = ConteudoEducacional("Live Backend Experience", Nivel.BASICO, 2)
    val cursoSQL = ConteudoEducacional("Curso de SQL com Firebird", Nivel.AVANCADO, 80)
    val desafioPython = ConteudoEducacional("Hello World com Python", Nivel.BASICO, 1)
    val cursoPython4Servers = ConteudoEducacional("Curso de Python aplicado a servidores", Nivel.AVANCADO, 120)
    
    val bootcampTop = Formacao("Bootcamp Top", mutableListOf(cursoPython, cursoPHP, liveBackEnd, cursoSQL, desafioPython))
    val bootcampPython = Formacao("Bootcamp Python", mutableListOf(cursoPython, desafioPython, cursoPython4Servers))
    val aceleracaoMercadoTrabalho = Formacao("Aceleração Mercado de Trabalho",  mutableListOf(cursoPHP, liveBackEnd, cursoSQL), 18)

    bootcampTop.matricular(usuario1)
    bootcampTop.matricular(usuario1)
    bootcampTop.matricular(usuario2)
    println()
    
    bootcampPython.matricular(usuario3)
    bootcampPython.matricular(usuario1)    
    println()
    
    aceleracaoMercadoTrabalho.matricular(usuario3)
    aceleracaoMercadoTrabalho.matricular(usuario2)
    println()
    
    bootcampTop.DetalharFormacao()
    aceleracaoMercadoTrabalho.DetalharFormacao()
}