import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {TecnicoService} from "../../../../services/tecnico.service";
import {Tecnico} from "../../../../models/tecnico";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-tecnico-create',
  templateUrl: './tecnico-create.component.html',
  styleUrls: ['./tecnico-create.component.css']
})
export class TecnicoCreateComponent implements OnInit {

  tecnico: Tecnico = {
    nome: '',
    cpf: '',
    telefone: ''
  }

  constructor(private router: Router, private service: TecnicoService, private snack: MatSnackBar) { }

  ngOnInit(): void {
  }

  cancel(): void{
    this.router.navigate(['tecnicos'])
  }

  toNewLineString(input: string): string{
    var lines = input.split('\\n');
    var output = "";
    lines.forEach(element => {
      output += element + "\n";
    });
    return output;
  }

  create():void{
    this.service.create(this.tecnico).subscribe((resposta)=>{
      this.router.navigate(['tecnicos'])
      this.service.message("Técnico criado com sucesso", 4000);
    }, err =>{
        if(err.error.error.match("CPF já cadastrado")){
          this.service.message(err.error.error, 4000);
        }else{

          let mensagemErro: string = ""
          let erro: any[] = err.error.errors;
          let duracao: number = 4000;

          for(let i = 0; i < erro.length; i++){
            this.service.message(erro[i].message, duracao);
            duracao += 4000;
          }
          console.log(err);
        }
    });
  }
}
