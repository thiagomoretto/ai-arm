# Makefile para compilação de LaTeX
#
# principais comandos:
#   make            : gera o .dvi
#   make arquivo.pdf   : gera o .pdf
#   make arquivo.ps    : gera o .ps
#   make arquivo2.ps    : gera o .ps com duplas páginas.
#   make clean      : apaga os arquivos temp. do LaTeX
#   make bib        : roda o BibTeX
#   make index      : roda o makeindex
#
# by Jomi
#

TARGET  = artigo

TEX = $(shell ls *.tex)
STY = $(shell ls *.sty 2> /dev/null)
BST = $(shell ls *.bst 2> /dev/null)
CLS = $(shell ls *.cls 2> /dev/null)

#figuras
FIG = $(shell find . -name *.fig)
DIA = $(shell find . -name *.dia)
EPS = $(FIG:.fig=.eps) $(DIA:.dia=.eps)
PDF = $(FIG:.fig=.pdf) $(DIA:.dia=.pdf)


all: $(TARGET).pdf

$(TARGET).dvi: $(TEX) $(BST) $(STY) $(CLS) $(EPS)  $(TARGET).bbl 
	latex $(TARGET)

# Verifica citações/index (comando sempre executa com sucesso -- true)

#para bib
	@if [ ! -f .btmp ]; then echo x > .btmp; fi
	@rm .rerun 2> /dev/null || true

# gera novas refs
	@if ( grep "\\\\bibliography{" $(TARGET).tex > /dev/null ); then \
		grep "^\\\\citation" $(TARGET).aux > .btmp.new || true; \
	else \
		echo x > .btmp.new; \
	fi

# compara novas com antigas
	@if (diff .btmp.new .btmp > /dev/null); then \
		echo "";\
	else \
		echo mudaram as referencias; \
		make bib; \
		touch .rerun; \
	fi
	@rm .btmp.new;

# para index
	@if [ ! -f .itmp ]; then echo x > .itmp; fi

	@if [ -f $(TARGET).idx ]; then \
		cp $(TARGET).idx .itmp.new; \
	else \
		touch .itmp.new; \
	fi
	@if ( diff .itmp.new .itmp > /dev/null ); then \
		echo "";\
	else \
		echo mudou index; \
		make index; \
		touch .rerun; \
	fi
	@rm .itmp.new;

# verifica se precisa rodar latex denovo
	@if [ -f .rerun ]; then \
		echo "denovo latex (bib/ind)"; \
		latex $(TARGET) > /dev/null; \
		rm .rerun; \
	fi

	@while ( grep Rerun $(TARGET).log > /dev/null ); do \
		echo "denovo latex (rerun)"; \
		latex $(TARGET) > /dev/null; \
	done


%.eps: %.fig
	@echo fig2dev $(@:.eps=.fig)
	@fig2dev -L eps $(@:.eps=.fig) $@

%.eps: %.dia
	@echo dia $(@:.eps=.dia)
	@dia -e $@ $(@:.eps=.dia) 2> /dev/null

%.pdf: %.eps
	epstopdf $(@:.pdf=.eps) > $@


$(TARGET).pdf: $(TARGET).dvi
	dvips -t a4 -Ppdf -G0 $(TARGET).dvi
	ps2pdf $(TARGET).ps $(TARGET).pdf
	rm $(TARGET).ps

$(TARGET).ps: $(TARGET).dvi
	dvips -t a4 $(TARGET) -o

$(TARGET)2.ps: $(TARGET).ps
	dvips -O -2cm,0cm -t letter $(TARGET).dvi -o $(TARGET).ps;psnup -n 2 -m30 -pletter $(TARGET).ps $(TARGET)2.ps; 

$(TARGET).ps.gz: $(TARGET).ps
	gzip -f $(TARGET).ps

$(TARGET).aux:
	latex $(TARGET) 

$(TARGET).bbl: 
	make bib

bib: $(TARGET).aux
	@rm .btmp 2> /dev/null || true 
	@if ( grep "\\\\bibliography{" $(TARGET).tex > /dev/null ); then \
		bibtex $(TARGET); \
		grep "^\\\\citation" $(TARGET).aux > .btmp; \
	fi


index:
	@if [ -f $(TARGET).idx ]; then \
		makeindex $(TARGET); \
		cp $(TARGET).idx .itmp; \
	fi

view: $(TARGET).dvi
	xdvi $(TARGET)

final: $(TARGET).dvi
	make bib
	make index
	latex $(TARGET) > /dev/null
	make $(TARGET).ps.gz
	make $(TARGET).pdf

clean:
	rm -f *.ps *.dvi *.lo? *.toc *.blg *.aux *.out core *.bbl *.idx *.ilg *.ind .itmp .btmp || true
	rm -rf auto || true
	find . -name *bak -exec rm {} \; || true
	find . -name *~ -exec rm {} \; || true

cleanpdf:
	find . -name "*.pdf" -exec rm {} \;

cleanps:
	find . -name "*.ps" -exec rm {} \;



