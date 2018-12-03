import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICmRequest } from 'app/shared/model/cm-request.model';

@Component({
    selector: 'jhi-cm-request-detail',
    templateUrl: './cm-request-detail.component.html'
})
export class CmRequestDetailComponent implements OnInit {
    cmRequest: ICmRequest;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmRequest }) => {
            this.cmRequest = cmRequest;
        });
    }

    previousState() {
        window.history.back();
    }
}
