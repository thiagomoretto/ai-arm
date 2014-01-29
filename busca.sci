// busca.sci
//
// Implementação da busca por descida de gradiente e têmpera
// simulada com finalidade didática. Trata apenas de um
// caso particular, onde o conjunto de estado é representado
// por uma função discreta unidimensional (um conjunto
// de números naturais [0:n]).
//

// Autor: Hemerson Pistori (pistori@ucdb.br)
// Data: 19/03/2007



// Busca por descida de gradiente
//
// x - Vetor que define o conjunto de estados
// y - Função de avaliação aplicada ao conjunto de estados
// estadoInicial - Inteiro indicando o estado inicial
// totalPassos - Quantidade de iterações até a busca terminar
function m=descidaGradiente(x,y,estadoInicial,totalPassos)
   estadoAtual = estadoInicial;
   iniciaGrafico("Descida Gradiente",x,y,estadoAtual)
   for i=1:totalPassos
     vizY = y(estadoAtual-1:estadoAtual+1)
     [minimo,pos] = mini(vizY);
     estadoAtual = (estadoAtual-2)+pos;
     if atualizaGrafico(x,y,estadoAtual,i) == 2
        break;
     end
   end
   m=estadoAtual;
endfunction

// Busca por têmpera simulada (simulated annealing)
//
// x - Vetor que define o conjunto de estados
// y - Função de avaliação aplicada ao conjunto de estados
// estadoInicial - Inteiro indicando o estado inicial
// T - Vetor que define a temperatura a cada passo
// k - Constante de Boltzmann
function m=simulatedAnnealing(x,y,estadoInicial,T,k)
   estadoAtual = estadoInicial;
   i = 1
   iniciaGrafico("Têmpera Simulada",x,y,estadoAtual)
   while T(i) != 0
     vizX = [estadoAtual-1 estadoAtual+1]
     proximoEstado = vizX(ceil(rand(1)*2))
     dX = y(estadoAtual)-y(proximoEstado)
     if dX > 0
        estadoAtual = proximoEstado
     else
        t = T(i)
        p = exp(-((abs(dX)/k*t)))
        if(rand(1)<=p)
           estadoAtual = proximoEstado
        end
     end
     if atualizaGrafico(x,y,estadoAtual,i) == 2
        break;
     end
     i = i + 1;
   end
   m=estadoAtual;
endfunction


// FUNÇÕES AUXILIARES - FUNÇÕES AUXILIARES - FUNÇÕES AUXILIARES -

// Exemplo de como utilizar as buscas
//
// estadoInicial - Inteiro indicando o estado inicial
function testa(estadoInicial)
  x = [0:50];
  y = ((x)-50)^3/100+1000*cos((x))
  y(10:30)=-x(10:30)^2+y(10:30)
  //descidaGradienteIngrime(x,y,estadoInicial,10)
  descidaGradiente(x,y,estadoInicial,10)
  T = [50:-1:0]
  simulatedAnnealing(x,y,estadoInicial,T,200000)
endfunction

// Função auxiliar que inicializa a janela gráfica
//
// tipoDaBusca - Nome da busca
// x - Domínio da função
// y - Imagem da função
// estadoInicial - Estado inicial da busca
function iniciaGrafico(tipoDaBusca,x,y,estadoInicial)
   xbasc();
   xset("background",color(170,180,130))
   plot2d(x,y);
   xset("mark size",1);
   plot2d(x,y,style=[1,1]);
   mostraIteracao(x,y,estadoAtual,0);
   x_message(['Iniciando '+tipoDaBusca+' com estado atual = '+string(estadoAtual)';
             'Clique Ok para continuar ?'],['Ok'])
endfunction

// Função auxiliar para mostrar o estado atual da busca
//
// x - Domínio da função
// y - Imagem da função
// estadoAtual - Estado atual da busca
// iteracao - Número da iteração atual
function r=atualizaGrafico(x,y,estadoAtual,iteracao)
   mostraIteracao(x,y,estadoAtual,iteracao);
   r=x_message(['Estado na Iteração '+string(iteracao)+' = '+string(estadoAtual)';
             'Continua ?'],['Sim','Nao'])
endfunction

// Função auxiliar para mostrar o número da iteração
// próximo do valor do estado atual no gráfico
//
// x - Domínio da função
// y - Imagem da função
// estado - Estado atual da busca
// iteracao - Número da iteração atual
function mostraIteracao(x,y,estado,iteracao)
   printf('y=%f',y(estado+1));
   r=xstringl(estado,y(estado+1),string(iteracao))
   xset("pattern",color(190,190,255))
   xfrect(r(1), r(2)+r(4)+1, r(3)+1, r(4)-3)
   xset("pattern",color(90,90,255))
   xrect(r(1), r(2)+r(4)+1, r(3)+1, r(4)-3)
   xstring(r(1)+1,r(2),string(iteracao))
endfunction
