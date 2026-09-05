import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-home',
  standalone: true,
  templateUrl: './home.component.html',
})
export class HomePage {
  constructor(private router: Router) {}
}
