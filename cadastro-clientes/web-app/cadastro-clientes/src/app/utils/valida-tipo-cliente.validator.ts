import { AbstractControl, FormGroup } from '@angular/forms';


export function ValidaTipoCliente(controleTipoCliente: string, controleValorTotalPatrimonio: string, controleValorTotalDividas: string) {

    return (formGroup: FormGroup) => {
        const tipoCliente = formGroup.controls[controleTipoCliente];
        const valorTotalPatrimonio = formGroup.controls[controleValorTotalPatrimonio];
        const valorTotalDividas = formGroup.controls[controleValorTotalDividas];

        if ((valorTotalPatrimonio.errors && !valorTotalPatrimonio.errors.ehObrigatorio) || (valorTotalDividas.errors && !valorTotalDividas.errors.ehObrigatorio)) {
            return;
        }

        if (tipoCliente.value == 2 && (valorTotalPatrimonio.value == null || valorTotalPatrimonio.value == 0)){
            valorTotalPatrimonio.setErrors({ ehObrigatorio: true });
        } else {
            valorTotalPatrimonio.setErrors(null);
        }

        if (tipoCliente.value == 3 && (valorTotalDividas.value == null || valorTotalDividas.value == 0)){
            valorTotalDividas.setErrors({ ehObrigatorio: true });
        } else {
            valorTotalDividas.setErrors(null);
        }
    }
}