import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { GroupsService } from '../../services/groups.service';
import { Groups } from '../../models/Groups';

@Component({
	selector: 'app-navigation',
	templateUrl: './navigation.component.html',
	styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
	
	navbarOpen = false;
	groups: Groups[];

	constructor(private router: Router, private groupsService: GroupsService) { }

	ngOnInit() {
		this.groupsService.get().subscribe(groups => this.groups = groups);
	}

	toggleNavbar() {
		this.navbarOpen = !this.navbarOpen;
	}
}
