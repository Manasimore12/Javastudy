import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { SharedNgZorroModule } from '../../../shared-ng-zorro.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Route, Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,
    ReactiveFormsModule,
    SharedNgZorroModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm !: FormGroup;
  isSpinning:boolean = false;

  constructor(private fb: FormBuilder,
    private service:AuthService,
    private massage:NzMessageService,
    private router:Router
  ) {
  this.loginForm = this.fb.group({
  email: [null, [Validators.required, Validators.email]],
  password: [null, [Validators.required]],
  })
}
login(){
  this.isSpinning = true;
  this.service.login(this.loginForm.value).subscribe((res)=>{
    if (res.userId != null){
      const user ={
        id:res.userId,
        role: res.userRole
      }
      StorageService.saveUser(user);
      StorageService.saveToken(res.jwt);
      if(StorageService.isAdminLoggedIn())
        this.router.navigateByUrl("/admin/dashboard");
      else if(StorageService.isCustomerLoggedIn())
        this.router.navigateByUrl("/customer/dashboard");
    } else {
      this.massage.error("Bad credentials", {nzDuration: 5000})
    }
    this.isSpinning = false;
  })
}
}
