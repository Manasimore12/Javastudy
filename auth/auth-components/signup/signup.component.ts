import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { SharedNgZorroModule } from '../../../shared-ng-zorro.module';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';


@Component({
  selector: 'app-signup',
  imports: [CommonModule,
    ReactiveFormsModule,
    SharedNgZorroModule,RouterModule],
  standalone: true,
  providers: [AuthService],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupForm !: FormGroup;
  isSpinning:boolean = false;

  constructor(private fb: FormBuilder,
    private service:AuthService
  ) {
  this.signupForm = this.fb.group({
  name: [null, [Validators.required]],
  email: [null, [Validators.required, Validators.email]],
  password: [null, [Validators.required]],
  confirmPassword: [null, [Validators.required]],
  }, {
  validators: this.matchPasswords 
})
}
  matchPasswords(group: FormGroup): { [key: string]: any } | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }
  
signup(){
  console.log(this.signupForm.value);
  this.service.register(this.signupForm.value).subscribe((res)=>{
    console.log(res)
  })
}
}
