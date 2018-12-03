import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICmAction } from 'app/shared/model/cm-action.model';

@Component({
    selector: 'jhi-cm-action-detail',
    templateUrl: './cm-action-detail.component.html'
})
export class CmActionDetailComponent implements OnInit {
    cmAction: ICmAction;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmAction }) => {
            this.cmAction = cmAction;
        });
    }

    previousState() {
        window.history.back();
    }
}
