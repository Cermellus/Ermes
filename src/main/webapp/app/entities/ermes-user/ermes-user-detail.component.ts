import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IErmesUser } from 'app/shared/model/ermes-user.model';

@Component({
    selector: 'jhi-ermes-user-detail',
    templateUrl: './ermes-user-detail.component.html'
})
export class ErmesUserDetailComponent implements OnInit {
    ermesUser: IErmesUser;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ermesUser }) => {
            this.ermesUser = ermesUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
