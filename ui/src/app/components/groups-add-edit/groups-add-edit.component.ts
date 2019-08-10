import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { GroupsService } from '../../services/groups.service';
import { Groups } from '../../models/Groups';

@Component({
	selector: 'app-groups-add-edit',
	templateUrl: './groups-add-edit.component.html',
	styleUrls: ['./groups-add-edit.component.css']
})
export class GroupsAddEditComponent implements OnInit {

	group: Groups = {name: ''};
	groupForm: FormGroup;
	id: string;

	constructor(private groupsService: GroupsService, private route:ActivatedRoute,
		private formBuilder: FormBuilder, private router: Router) {
		this.buildForm(this.group);
	}

	ngOnInit() {
		this.id = this.route.snapshot.params['id'];
		if (this.id) {
			this.groupsService.getById(this.id).subscribe(group => {
				this.group = group;
				this.buildForm(this.group);
			});
		}
	}

	save(group: Groups) {
		this.groupsService.save(group).subscribe(() => this.goBack());
	}

	update(group: Groups) {
		group.id = this.id;
		this.groupsService.update(group).subscribe(() => this.goBack());
	}

	buildForm(group: Groups) {
		this.groupForm = this.formBuilder.group({
			'name' : [group.name, Validators.required],
			'validate' : ''
		});
	}

	goBack() {
		this.router.navigate(['/groups']);
	}
}
