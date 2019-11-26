import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { GroupsService } from '../../services/groups.service';
import { Groups } from '../../models/Groups';

@Component({
	selector: 'app-groups',
	templateUrl: './groups.component.html',
	styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
	
	groups: Groups[];
	
	constructor(private groupsService: GroupsService, private router: Router) { }

	ngOnInit() {
		this.groupsService.get().subscribe(groups => this.groups = groups);
	}

	add() {
		this.router.navigate(['/groups_add']);
	};

	edit(id) {
		this.router.navigate(['/groups_edit', id]);
	};

	remove({id, index}) {
		if(!confirm('Are you sure?')) {
			return;
		}

		this.groupsService.delete(id).subscribe(()=> this.groups.splice(index, 1));
	}
}
