import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { navbar } from './navbar/navbar';
import { student } from '../features/manage/student.componet';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [navbar, RouterOutlet, student],
  templateUrl: './main.layout.html',
})
export class MainLayout {}
