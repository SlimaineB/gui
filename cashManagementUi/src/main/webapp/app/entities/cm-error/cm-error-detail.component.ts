import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICmError } from 'app/shared/model/cm-error.model';

@Component({
    selector: 'jhi-cm-error-detail',
    templateUrl: './cm-error-detail.component.html'
})
export class CmErrorDetailComponent implements OnInit {
    cmError: ICmError;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmError }) => {
            this.cmError = cmError;
        });
    }

    previousState() {
        window.history.back();
    }
}
