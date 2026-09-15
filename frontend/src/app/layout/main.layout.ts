import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { navbar } from './navbar/navbar.component';
import { students } from '../features/manage/students/students.component';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [navbar, RouterOutlet, students],
  templateUrl: './main.layout.html',
})
export class MainLayout {}
